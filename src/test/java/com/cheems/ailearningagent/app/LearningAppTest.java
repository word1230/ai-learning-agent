package com.cheems.ailearningagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class LearningAppTest {

    @Resource
    private  LearningApp app;

    @Test
    void call() {

        String chatID = "1111333";
        String message = "我想要知道mcp协议，请你讲一讲";
        String call = app.call(message, chatID);
        System.out.println(call );
        Assertions.assertNotNull(call);


    }

    @Test
    void doChatWithReport() {

        LearningApp.LearningReport learningReport = app.doChatWithReport("你好，我是cheems，我想要学习redis", "1");
        System.out.println(learningReport.toString());
        Assertions.assertNotNull(learningReport);
    }

    @Test
    void doChatWithRag() {


        String s = app.doChatWithRag("最近新出了一个概念叫做Model Context Protocol，简称 MCP，请你讲一讲", "111111111");

        Assertions.assertNotNull(s);
    }

    @Test
    void doChatWithCloudRag() {

        String s = app.doChatWithCloudRag("最近新出了一个概念叫做MCP，请你讲一讲", "12325556");
        Assertions.assertNotNull(s);
    }
}