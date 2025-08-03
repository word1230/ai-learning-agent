package com.cheems.ailearningagent.rag;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LearningAppDocumentLoaderTest {


    @Resource
    private LearningAppDocumentLoader learningAppDocumentLoader;
    @Test
    void loadMarkdown() {
        List<Document> documents = learningAppDocumentLoader.loadMarkdown();
        System.out.println(documents);
    }
}