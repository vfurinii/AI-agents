package com.chatbot.demo.llm;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class OpenAiClient implements LlmClient {

    private final WebClient client;

    public OpenAiClient(WebClient client) {
        this.client = client;
    }

    @Override
    public String ask(String prompt) {

        Map<String, Object> body = Map.of(
                "model", "gpt-4.1-mini",
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                )
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
