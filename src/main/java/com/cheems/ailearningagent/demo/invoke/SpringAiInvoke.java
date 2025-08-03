package com.cheems.ailearningagent.demo.invoke;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;


@Component
public class SpringAiInvoke {


    private  final ChatClient chatClient;


    public SpringAiInvoke(ChatClient.Builder builder){
        this.chatClient =builder
                .build();
    }

    public String chat(String input){
        return  this.chatClient.prompt()
                .user(input)
                .call()
                .content();
    }

}
