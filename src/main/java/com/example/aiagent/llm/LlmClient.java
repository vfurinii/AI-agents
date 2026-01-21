package com.example.aiagent.llm;

import com.theokanning.openai.service.OpenAiService;
import com.theokanning.openai.completion.chat.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.List;

@Component
public class LlmClient {

    private final OpenAiService service;
    private final String model;

    public LlmClient(
            @Value("${openai.api-key}") String apiKey,
            @Value("${openai.model}") String model
    ) {
        this.service = new OpenAiService(apiKey, Duration.ofSeconds(60));
        this.model = model;
    }

    public ChatCompletionResult ask(
            List<ChatMessage> messages,
            List<ChatFunction> functions
    ) {
        ChatCompletionRequest request = ChatCompletionRequest.builder()
                .model(model)
                .messages(messages)
                .functions(functions)
                .functionCall(ChatCompletionRequest.ChatCompletionRequestFunctionCall.of("auto"))
                .build();
        return service.createChatCompletion(request);
    }
}
