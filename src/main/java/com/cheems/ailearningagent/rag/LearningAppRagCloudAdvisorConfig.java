package com.cheems.ailearningagent.rag;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentCloudReader;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrievalAdvisor;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class LearningAppRagCloudAdvisorConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String dashScopeApiKey;



    @Bean
    public Advisor learningAppRagCloudAdvisor(){
        DashScopeApi dashScopeApi = new DashScopeApi.Builder().apiKey(dashScopeApiKey).build();

        DashScopeDocumentRetriever mcp协议 = new DashScopeDocumentRetriever(dashScopeApi,
                DashScopeDocumentRetrieverOptions.builder()
                        .withIndexName("mcp协议").build());
        return RetrievalAugmentationAdvisor.builder()
                .documentRetriever(mcp协议)
                .build();

    }

}
