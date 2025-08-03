package com.cheems.ailearningagent.advisor;

import org.springframework.ai.chat.client.ChatClientRequest;
import org.springframework.ai.chat.client.ChatClientResponse;
import org.springframework.ai.chat.client.advisor.api.AdvisorChain;
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;

import java.util.Map;

/**
 * 二次阅读的advisor， 强化提示词的作用
 */
public class ReReadingAdvisor implements BaseAdvisor {

	private static final String DEFAULT_RE2_ADVISE_TEMPLATE = """
			{re2_input_query}
			Read the question again: {re2_input_query}
			""";

	private final String re2AdviseTemplate;

	private int order = 0;

	public ReReadingAdvisor() {
		this.re2AdviseTemplate = DEFAULT_RE2_ADVISE_TEMPLATE;
	}



	@Override
	public ChatClientRequest before(ChatClientRequest chatClientRequest, AdvisorChain advisorChain) {
		String augmentedUserText = PromptTemplate.builder()
				.template(this.re2AdviseTemplate)
				.variables(Map.of("re2_input_query", chatClientRequest.prompt().getUserMessage().getText()))
				.build()
				.render();

		return chatClientRequest.mutate()
				.prompt(chatClientRequest.prompt().augmentUserMessage(augmentedUserText))
				.build();
	}

	@Override
	public ChatClientResponse after(ChatClientResponse chatClientResponse, AdvisorChain advisorChain) {
		return chatClientResponse;
	}


	@Override
	public int getOrder() {
		return this.order;
	}

	public ReReadingAdvisor withOrder(int order) {
		this.order = order;
		return this;
	}

}