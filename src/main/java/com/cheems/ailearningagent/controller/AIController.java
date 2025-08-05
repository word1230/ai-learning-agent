package com.cheems.ailearningagent.controller;

import com.cheems.ailearningagent.agent.CheemsManus;
import com.cheems.ailearningagent.agent.ToolCallAgent;
import com.cheems.ailearningagent.app.LearningApp;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeEditor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
public class AIController {



    @Resource
    private LearningApp learningApp;

    @Resource
    private ToolCallback[] allTools;

    @Resource
    private ChatModel dashscopeChatModel;


    @GetMapping("/learning_app/chat/sync")
    public String doChatWithLearningAppSync(String query,String chatId){
        return  learningApp.call(query,chatId);
    }


    @GetMapping(value = "/learning_app/chat/sse",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> doChatWithLearningAppSSE(String query,String chatId){
        return  learningApp.doChatByStream(query,chatId);
    }

    public SseEmitter doChatWithCheemsManus(String query){
        CheemsManus cheemsManus = new CheemsManus(allTools,dashscopeChatModel);
        return cheemsManus.runStream(query);
    }







}
