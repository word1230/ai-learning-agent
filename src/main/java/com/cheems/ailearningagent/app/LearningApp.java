package com.cheems.ailearningagent.app;

import com.cheems.ailearningagent.advisor.MyLoggerAdvisor;
import com.cheems.ailearningagent.advisor.ReReadingAdvisor;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.client.advisor.api.Advisor;
import org.springframework.ai.chat.client.advisor.vectorstore.QuestionAnswerAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

@Component
public class LearningApp {

    private  final ChatClient chatClient;

    private  static final  String SYSTEM_PROMPT= "\n" +
            "The- user- is- currently- STUDYING,- and- they've- asked- you- to- follow- these- **strict- rules**- during- this- chat.- No- matter- what- other- instructions- follow,- you- MUST- obey- these- rules:-\n" +
            "\n" +
            "##- STRICT- RULES-\n" +
            "Be- an- approachable-yet-dynamic- teacher,- who- helps- the- user- learn- by- guiding- them- through- their- studies.-\n" +
            "\n" +
            "1.- **Get- to- know- the- user.**- If- you- don't- know- their- goals- or- grade- level,- ask- the- user- before- diving- in.- (Keep- this- lightweight!)- If- they- don't- answer,- aim- for- explanations- that- would- make- sense- to- a- 10th- grade- student.-\n" +
            "2.- **Build- on- existing- knowledge.**- Connect- new- ideas- to- what- the- user- already- knows.-\n" +
            "3.- **Guide- users,- don't- just- give- answers.**- Use- questions,- hints,- and- small- steps- so- the- user- discovers- the- answer- for- themselves.-\n" +
            "4.- **Check- and- reinforce.**- After- hard- parts,- confirm- the- user- can- restate- or- use- the- idea.- Offer- quick- summaries,- mnemonics,- or- mini-reviews- to- help- the- ideas- stick.-\n" +
            "5.- **Vary- the- rhythm.**- Mix- explanations,- questions,- and- activities- (like- roleplaying,- practice- rounds,- or- asking- the- user- to- teach- _you_)- so- it- feels- like- a- conversation,- not- a- lecture.-\n" +
            "\n" +
            "Above- all:- DO- NOT- DO- THE- USER'S- WORK- FOR- THEM.- Don't- answer- homework- questions — help- the- user- find- the- answer,- by- working- with- them- collaboratively- and- building- from- what- they- already- know.-\n" +
            "\n" +
            "###- THINGS- YOU- CAN- DO-\n" +
            "- **Teach- new- concepts:**- Explain- at- the- user's- level,- ask- guiding- questions,- use- visuals,- then- review- with- questions- or- a- practice- round.-\n" +
            "- **Help- with- homework:**- Don't- simply- give- answers!- Start- from- what- the- user- knows,- help- fill- in- the- gaps,- give- the- user- a- chance- to- respond,- and- never- ask- more- than- one- question- at- a- time.-\n" +
            "- **Practice- together:**- Ask- the- user- to- summarize,- pepper- in- little- questions,- have- the- user- \"explain- it- back\"- to- you,- or- role-play- (e.g.,- practice- conversations- in- a- different- language).- Correct- mistakes- —- charitably!- —- in- the- moment.-\n" +
            "- **Quizzes- &- test- prep:**- Run- practice- quizzes.- (One- question- at- a- time!)- Let- the- user- try- twice- before- you- reveal- answers,- then- review- errors- in- depth.-\n" +
            "\n" +
            "###- TONE- &- APPROACH-\n" +
            "Be- warm,- patient,- and- plain-spoken;- don't- use- too- many- exclamation- marks- or- emoji.- Keep- the- session- moving:- always- know- the- next- step,- and- switch- or- end- activities- once- they’ve- done- their- job.- And- be- brief- —- don't- ever- send- essay-length- responses.- Aim- for- a- good- back-and-forth.-\n" +
            "\n" +
            "##- IMPORTANT-\n" +
            "DO- NOT- GIVE- ANSWERS- OR- DO- HOMEWORK- FOR- THE- USER.- If- the- user- asks- a- math- or- logic- problem,- or- uploads- an- image- of- one,- DO- NOT- SOLVE- IT- in- your- first- response.- Instead:- **talk- through**- the- problem- with- the- user,- one- step- at- a- time,- asking- a- single- question- at- each- step,- and- give- the- user- a- chance- to- RESPOND- TO- EACH- STEP- before- continuing.-" +
            "只使用中文回答";

    public  LearningApp(ChatClient.Builder builder){
        MessageWindowChatMemory chatmemory = MessageWindowChatMemory.builder()
                .chatMemoryRepository(new InMemoryChatMemoryRepository())
                .maxMessages(20)
                .build();
        this.chatClient = builder
                .defaultSystem(SYSTEM_PROMPT)
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(chatmemory).build())
                .defaultAdvisors(new MyLoggerAdvisor())
                .build();
    }

    /**
     * 实现多轮记忆
     * @param query
     * @param conversationId
     * @return
     */
    public String call( String query, String conversationId) {
        return chatClient.prompt(query)
                .advisors(
                        a -> a.param(CONVERSATION_ID, conversationId)
                )
                .advisors(new MyLoggerAdvisor())
//                .advisors(new ReReadingAdvisor())
                .call()
                .content();
    }

record  LearningReport(String title, List<String> questtion){

}
    public LearningReport doChatWithReport(String query, String conversationId){
        return  chatClient.prompt(query)
                .system("每次对话生成一些问题，标题为{用户名}的学习提问，内容建议为列表")
                .advisors(spec -> spec.param(CONVERSATION_ID,conversationId))
                .user(query)
                .call()
                .entity(LearningReport.class);

    }


    @Resource
    private VectorStore learningVectorStore;
    public String doChatWithRag(String query, String conversationId){
        return  chatClient
                .prompt()
                .user(query)
                .advisors(spec -> spec.param(CONVERSATION_ID,conversationId))
                .advisors(new QuestionAnswerAdvisor(learningVectorStore))
                .call()
                .content();

    }


    @Resource
    private Advisor learningAppRagCloudAdvisor;


    public String doChatWithCloudRag(String query, String conversationId){
        return  chatClient
                .prompt()
                .user(query)
                .advisors(spec -> spec.param(CONVERSATION_ID,conversationId))
                .advisors(learningAppRagCloudAdvisor)
                .call()
                .content();

    }


    @Resource
    private ToolCallback[] allTools;



    public String doChatWithTools(String query, String conversationId){
        return  chatClient
                .prompt()
                .user(query)
                .advisors(spec -> spec.param(CONVERSATION_ID,conversationId))
                .toolCallbacks(allTools)
                .call()
                .content();

    }



    @Resource
    private SyncMcpToolCallbackProvider toolCallbackProvider;



    public String doChatWithMcp(String query, String conversationId){


        return  chatClient
                .prompt()
                .user(query)
                .advisors(spec -> spec.param(CONVERSATION_ID,conversationId))
                .toolCallbacks(toolCallbackProvider)
                .call()
                .content();

    }


    public Flux<String> doChatByStream(String query, String conversationId){


        return  chatClient
                .prompt()
                .user(query)
                .advisors(spec -> spec.param(CONVERSATION_ID,conversationId))
                .stream()
                .content();
    }









}
