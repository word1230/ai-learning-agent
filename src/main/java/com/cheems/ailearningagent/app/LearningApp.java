package com.cheems.ailearningagent.app;

import com.cheems.ailearningagent.advisor.MyLoggerAdvisor;
import com.cheems.ailearningagent.advisor.ReReadingAdvisor;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Component
public class LearningApp {

    private  final ChatClient chatClient;

    private  static final  String SYSTEM_PROMPT= "你是一个擅长ai领域知识的专家，你会对我的问题进行讲解" +
            "回答尽量简短";

    public  LearningApp(ChatClient.Builder builder){
        MessageWindowChatMemory chatmemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(5)
                .build();
        this.chatClient = builder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatmemory).build())
                .defaultAdvisors(new MyLoggerAdvisor())
                .build();
    }

    /**
     * 实现多轮记忆
     * @param query
     * @param conversationId
     * @return
     */
    public String call( String query, String conversationId) {
        return chatClient.prompt(query)
                .advisors(
                        a -> a.param(CONVERSATION_ID, conversationId)
                )
                .advisors(new MyLoggerAdvisor())
//                .advisors(new ReReadingAdvisor())
                .call()
                .content();
    }

record  LearningReport(String title, List<String> questtion){

}
    public LearningReport doChatWithReport(String query, String conversationId){
        return  chatClient.prompt(query)
                .system("每次对话生成一些问题，标题为{用户名}的学习提问，内容建议为列表")
                .advisors(spec -> spec.param(CONVERSATION_ID,conversationId))
                .user(query)
                .call()
                .entity(LearningReport.class);

    }


    @Resource
    private VectorStore learningVectorStore;
    public String doChatWithRag(String query, String conversationId){
        return  chatClient
                .prompt()
                .user(query)
                .advisors(spec -> spec.param(CONVERSATION_ID,conversationId))
                .advisors(new QuestionAnswerAdvisor(learningVectorStore))
                .call()
                .content();

    }


    @Resource
    private Advisor learningAppRagCloudAdvisor;


    public String doChatWithCloudRag(String query, String conversationId){
        return  chatClient
                .prompt()
                .user(query)
                .advisors(spec -> spec.param(CONVERSATION_ID,conversationId))
                .advisors(learningAppRagCloudAdvisor)
                .call()
                .content();

    }







}
