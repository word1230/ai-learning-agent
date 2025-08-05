package com.cheems.ailearningagent.agent;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgentOptions;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.ToolResponseMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.model.tool.ToolExecutionResult;
import org.springframework.ai.tool.ToolCallback;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 处理工具调用
 */
@Slf4j
@EqualsAndHashCode(callSuper = true)
@Data
public class ToolCallAgent extends ReActAgent {


    //获取可用的工具
    private  final ToolCallback[] availableTools;


    //保存工具调用的响应
    private ChatResponse  toolCallChatResponse;

    //工具调用管理者

    private  final ToolCallingManager toolCallingManager;

    //禁止内置的工具调用逻辑
    private  final ChatOptions chatOptions;

    //构造器
    public ToolCallAgent(ToolCallback[] availableTools){
        super();
        this.availableTools =availableTools;
        this.toolCallingManager = ToolCallingManager.builder().build();
        this.chatOptions = DashScopeChatOptions.builder()
                .withInternalToolExecutionEnabled(false)
                .build();
    }






    @Override
    public boolean think() {
        //保存消息
        if(getNextPrompt() != null && !getNextPrompt().isEmpty()){
            UserMessage userMessage = new UserMessage(getNextPrompt());
            getMessageList().add(userMessage);
        }

        //将消息列表作为prompt，实现记忆
        List<Message> messageList = getMessageList();
        Prompt prompt =new Prompt(messageList,chatOptions);


        try {
            //调用ai 获取调用工具的响应
            ChatResponse chatResponse = getChatClient().prompt(prompt)
                    .system(getSystemPrompt())
                    .toolCallbacks(availableTools)
                    .call()
                    .chatResponse();

            //记录响应 ,用于act
            toolCallChatResponse =chatResponse;

            //获取响应 及调用的工具
            AssistantMessage assistantMessage = chatResponse.getResult().getOutput();
            String result = assistantMessage.getText();
            List<AssistantMessage.ToolCall> toolCalls = assistantMessage.getToolCalls();
            String toolCallInfo = toolCalls.stream()
                    .map(toolCall -> String.format("工具名称: %s,参数：%s", toolCall.name(), toolCall.arguments()))
                    .collect(Collectors.joining("\n"));

            log.info(getName() + "的思考" + result);
            log.info(getName() + "选择了" + toolCalls.size()+"个工具来调用");
            log.info(toolCallInfo);

            //同时如果不需要调用工具 记录不需要 , 需要调用，act中记录
            if (toolCalls.isEmpty()){
                getMessageList().add(assistantMessage);
                return false;
            }else {
                return  true;
            }
        } catch (Exception e) {
            log.info(getName() +  "的思考过程出现了问题" + e.getMessage());
            getMessageList().add(
                    new AssistantMessage("处理时遇到错误"+e.getMessage())
            );
            return false;
        }



    }









    @Override
    public String act() {

        if(!toolCallChatResponse.hasToolCalls()){
            return  "没有工具调用";
        }
        Prompt prompt = new Prompt(getMessageList() ,chatOptions);
        //调用工具
        ToolExecutionResult toolExecutionResult = toolCallingManager.executeToolCalls(prompt, toolCallChatResponse);
        //记录结果
        setMessageList(toolExecutionResult.conversationHistory());

        //日志 当前工具调用结果
        //获取最新的工具调用结果
        ToolResponseMessage toolResponseMessage = (ToolResponseMessage) CollUtil.getLast(toolExecutionResult.conversationHistory());
        String collect = toolResponseMessage.getResponses().stream()
                .map(response -> "工具：" + response.name() + "完成了任务，调用结果：" + response.responseData())
                .collect(Collectors.joining());
        log.info(collect);

        //如果说调用了终止工具，则修改状态
        boolean b = toolResponseMessage.getResponses().stream()
                .anyMatch(response -> "doTerminate".equals(response.name()));
        if(b){
            setState(AgentState.FINISHED);
        }
        return collect;
    }
}
