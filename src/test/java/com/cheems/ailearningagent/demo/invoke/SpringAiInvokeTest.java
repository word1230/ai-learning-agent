package com.cheems.ailearningagent.demo.invoke;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpringAiInvokeTest {


    @Resource
    private SpringAiInvoke invoke;

    @Test
    void test(){
        String chat = invoke.chat("你好，我是cheems");
        System.out.println(chat);
    }


}