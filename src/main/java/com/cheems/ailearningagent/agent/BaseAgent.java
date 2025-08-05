package com.cheems.ailearningagent.agent;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.internal.StringUtil;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Data
public abstract class BaseAgent {

    //基础属性
    private  String name;

    //提示词
    private String SystemPrompt;
    private String nextPrompt;

    //状态
    private AgentState state = AgentState.IDLE;

    //执行控制
    private  int maxSteps = 10;
    private  int currentStep =0 ;

    //llm
    private ChatClient chatClient;

    // 手动完成记忆

    private List<Message> messageList = new ArrayList<>();


    //执行单个步骤

    public abstract  String step();

    //清理资源
    protected  void cleanup(){

    }

    //运行方法

    public String run(String userPrompt){

        //检验参数
        if(StringUtil.isBlank(userPrompt)){
            throw  new RuntimeException("Cannot run agent with empty user prompt");
        }
        //判断状态
        if(this.state != AgentState.IDLE){
                 throw  new RuntimeException("Cannot run agent from state:"+this.state);
        }

        //更改状态
        state = AgentState.RUNNING;
        //记录消息上下文
        messageList.add(new UserMessage(userPrompt));
        //保存结果到列表中
        List<String> results = new ArrayList<>();

        try {
            for (int i=0; i<maxSteps && state!=AgentState.FINISHED ; i++){
                int stepNumber =i +1;
                currentStep = stepNumber;
                log.info("Executing step" + stepNumber + "/"+maxSteps);
                //单步执行
                String stepResult = step();
                String result = "Step" + stepNumber + ":" +stepResult;
                results.add(result);
            }
            //检测步数是否超出，更新状态
            if(currentStep >= maxSteps){
                state = AgentState.FINISHED;
                results.add("Terminated: Reached max step ("+ maxSteps +")");
            }
            return  String.join("\n",results);
        } catch (Exception e) {
           state = AgentState.ERROR;
           log.error("Error executing agent ",e);
           return  "执行错误"+ e.getMessage();
        }finally {
            this.cleanup();
        }

    }





}
