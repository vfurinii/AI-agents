package com.example.aiagent.agent;

import com.example.aiagent.dto.OpenAiChatRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenAiService {
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    @Value("${openai.api-key}")
    private String openApiKey;

    public String sendChatRequest(OpenAiChatRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openApiKey);

        HttpEntity<OpenAiChatRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                OPENAI_API_URL,
                HttpMethod.POST,
                entity,
                String.class
        );

        return response.getBody();
    }
}
