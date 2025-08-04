package com.cheems.ailearningagent.app;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

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

    @Test
    void doChatWithTools() {
        // 测试联网搜索问题的答案
        testMessage("我想知道有关 mcp 的最新消息， 请你给出链接");

        // 测试网页抓取：恋爱案例分析
        testMessage("最近和学习效率不高，看看编程导航网站（codefather.cn）有没有学习方法");

        // 测试资源下载：图片下载
        testMessage("直接下载一张适合做手机壁纸的学习激励图片为文件");

        // 测试终端操作：执行代码
        testMessage("执行 Python3 脚本来生成数据分析报告");

        // 测试文件操作：保存用户档案
        testMessage("保存我的学习记录为文件");

        // 测试 PDF 生成
        testMessage("生成一份‘我的学习计划的’PDF，包含时间安排，任务安排");
    }

    private void testMessage(String message) {

        String chatId = UUID.randomUUID().toString();
        String answer = app.doChatWithTools(message, chatId);
        Assertions.assertNotNull(answer);
    }

}