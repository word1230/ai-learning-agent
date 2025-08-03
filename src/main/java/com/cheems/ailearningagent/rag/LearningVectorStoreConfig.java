package com.cheems.ailearningagent.rag;

import jakarta.annotation.Resource;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基于内存的向量数据库
 */
@Component

public class LearningVectorStoreConfig {

    @Resource
    private  LearningAppDocumentLoader learningAppDocumentLoader;

    @Bean
    VectorStore learningVectorStore(EmbeddingModel embeddingModel){
        SimpleVectorStore simpleVectorStore = SimpleVectorStore.builder(embeddingModel).build();
        List<Document> documents = learningAppDocumentLoader.loadMarkdown();
        simpleVectorStore.add(documents);
        return  simpleVectorStore;
    }
}
