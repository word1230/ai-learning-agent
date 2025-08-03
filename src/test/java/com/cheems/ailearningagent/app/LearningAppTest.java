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

        String chatID = "1";
        String message = "你好，我是cheems";
        String call = app.call(message, chatID);
        System.out.println(call );
        Assertions.assertNotNull(call);


    }
}