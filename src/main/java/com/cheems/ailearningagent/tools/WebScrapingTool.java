package com.cheems.ailearningagent.tools;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.IOException;

/**
 * 网页抓取工具
 */
public class WebScrapingTool {


    @Tool(description = "Scrap the content of a web page")
    public String scrapWebPage(@ToolParam(description = "URL of the web page to scrap")String url){

        try {
            Document elements = Jsoup.connect(url).get();
            return elements.html();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
