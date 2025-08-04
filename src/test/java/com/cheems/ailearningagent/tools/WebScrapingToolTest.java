package com.cheems.ailearningagent.tools;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.annotation.Tool;

import static org.junit.jupiter.api.Assertions.*;

class WebScrapingToolTest {

    @Test
    void Test(){

        WebScrapingTool webScrapingTool = new WebScrapingTool();
        String s = webScrapingTool.scrapWebPage("https://www.baidu.com");
        Assertions.assertNotNull(s);
    }
}