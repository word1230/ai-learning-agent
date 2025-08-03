package com.cheems.ailearningagent.demo.invoke;

import dev.langchain4j.community.model.dashscope.QwenChatModel;
import dev.langchain4j.model.chat.ChatModel;

public class LangChainInvoke {

    public static void main(String[] args) {
        ChatModel qwenModel = QwenChatModel.builder()
                .apiKey(TestApiKey.API_KEY)
                .modelName("qwen-plus")
                .build();

        String chat = qwenModel.chat("你好，我是cheems");
        System.out.println(chat);

    }
}
