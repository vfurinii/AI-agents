package com.example.aiagent.agent;

import com.example.aiagent.dto.OpenAiChatRequest;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class OpenAiService {
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";
    private static final String OPENAI_API_KEY = "your_openai_api_key_here"; //
    public String sendChatRequest(OpenAiChatRequest request) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(OPENAI_API_KEY);

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
