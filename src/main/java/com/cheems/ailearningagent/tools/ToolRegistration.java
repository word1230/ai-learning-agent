package com.cheems.ailearningagent.tools;

import org.springframework.ai.support.ToolCallbacks;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolRegistration {


    @Bean
    public ToolCallback[] allTools(){
        FileOperationTool fileOperationTool = new FileOperationTool();
        PDFGenerationTool pdfGenerationTool = new PDFGenerationTool();
        ResourceDownloadTool resourceDownloadTool = new ResourceDownloadTool();
        TerminalOperationTool terminalOperationTool = new TerminalOperationTool();
        WebScrapingTool webScrapingTool = new WebScrapingTool();
        WebSearchTools webSearchTools = new WebSearchTools();
        TerminateTool terminateTool = new TerminateTool();

        return ToolCallbacks.from(
                fileOperationTool,
                pdfGenerationTool,
                resourceDownloadTool,
                terminalOperationTool,
                webScrapingTool,
                webSearchTools,
                terminateTool
        );
    }


}
