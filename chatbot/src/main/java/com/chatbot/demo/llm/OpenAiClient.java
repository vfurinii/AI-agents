package com.chatbot.demo.llm;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
public class OpenAiClient implements LlmClient {

    private final WebClient client;

    public OpenAiClient(WebClient client) {
        this.client = client;
    }

    @Override
    public String ask(List<Map<String, String>> messages) {

        // Add system message at the beginning for context
        List<Map<String, String>> allMessages = new ArrayList<>();
        allMessages.add(Map.of("role", "system", "content", "You are a helpful and friendly chatbot assistant."));
        allMessages.addAll(messages);

        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", allMessages
        );

        return client.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(Map.class)
                .map(r -> ((Map) ((List) r.get("choices")).get(0))
                        .get("message"))
                .map(m -> ((Map) m).get("content").toString())
                .block();
    }
}
