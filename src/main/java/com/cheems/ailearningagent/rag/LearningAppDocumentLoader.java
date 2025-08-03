package com.cheems.ailearningagent.rag;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.markdown.MarkdownDocumentReader;
import org.springframework.ai.reader.markdown.config.MarkdownDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class LearningAppDocumentLoader {


    private final ResourcePatternResolver resourcePatternResolver;

    LearningAppDocumentLoader(ResourcePatternResolver resourcePatternResolver) {
        this.resourcePatternResolver = resourcePatternResolver;
    }

   public List<Document> loadMarkdown() {


    List<Document> alldocuments = new ArrayList<>();
       try {
           Resource[] resources = resourcePatternResolver.getResources("classpath:document/*.md");
           for (Resource resource : resources) {
               String filename = resource.getFilename();
               MarkdownDocumentReaderConfig config = MarkdownDocumentReaderConfig.builder()
                       .withHorizontalRuleCreateDocument(true)
                       .withIncludeCodeBlock(false)
                       .withIncludeBlockquote(false)
                       .withAdditionalMetadata("filename", filename)
                       .build();
               MarkdownDocumentReader reader = new MarkdownDocumentReader(resource, config);
               alldocuments.addAll(reader.get());
           }
       } catch (IOException e) {
        log.error("文档加载失败",e);
       }
    return alldocuments;

    }

}
