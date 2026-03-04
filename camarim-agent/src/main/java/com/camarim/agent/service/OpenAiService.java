package com.camarim.agent.service;

import com.camarim.agent.config.CamarimProperties;
import com.camarim.agent.dto.ChatMessage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class OpenAiService {

    private final WebClient webClient;
    private final String model;
    private final CamarimProperties camarimProperties;
    private final ObjectMapper objectMapper;

    public OpenAiService(
            @Value("${openai.api-key}") String apiKey,
            @Value("${openai.model}") String model,
            @Value("${openai.base-url}") String baseUrl,
            CamarimProperties camarimProperties
    ) {
        this.model = model;
        this.camarimProperties = camarimProperties;
        this.objectMapper = new ObjectMapper();

        this.webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + apiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String chat(List<ChatMessage> messages) {
        try {
            // Adiciona o system prompt como primeira mensagem
            List<ChatMessage> allMessages = new ArrayList<>();
            allMessages.add(new ChatMessage("system", getSystemPrompt()));
            allMessages.addAll(messages);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("messages", allMessages);
            requestBody.put("temperature", 0.7);
            requestBody.put("max_tokens", 500);

            String response = webClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.debug("OpenAI Response: {}", response);

            // Extrai a resposta do assistant
            JsonNode rootNode = objectMapper.readTree(response);
            return rootNode
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

        } catch (Exception e) {
            log.error("Erro ao chamar OpenAI API", e);
            return "Desculpe, ocorreu um erro. Por favor, tente novamente em instantes. 💕";
        }
    }

    private String getSystemPrompt() {
        StringBuilder servicesInfo = new StringBuilder();
        if (!camarimProperties.getServices().isEmpty()) {
            servicesInfo.append("\nServiços e Preços disponíveis:\n");
            for (var service : camarimProperties.getServices()) {
                servicesInfo.append(String.format("- %s: R$ %.2f",
                    service.getName(),
                    service.getPrice()));
                if (service.getDescription() != null && !service.getDescription().isEmpty()) {
                    servicesInfo.append(String.format(" - %s", service.getDescription()));
                }
                servicesInfo.append("\n");
            }
        }

        return String.format("""
            Você é a assistente virtual da %s, um salão de beleza localizado em %s.
            Seu atendimento deve ser %s.
            %s
            
            Suas funções:
            - Responder dúvidas sobre serviços, preços e disponibilidade
            - Informar formas de pagamento
            - Ajudar a agendar horários (direcionar para contato direto)
            - Divulgar novidades, promoções e pacotes especiais
            - Incentivar o agendamento
            
            Regras importantes:
            - Seja clara e objetiva
            - Use linguagem moderna e acolhedora
            - Sempre incentive o agendamento
            - Quando perguntarem sobre preços, use EXATAMENTE os valores da lista acima
            - Quando não souber uma informação, informe que irá verificar com a equipe
            - Use emojis de forma moderada para deixar a conversa mais calorosa
            
            Tom de voz: %s
            
            Instagram: %s
            """,
                camarimProperties.getStore().getName(),
                camarimProperties.getStore().getLocation(),
                camarimProperties.getAssistant().getStyle(),
                servicesInfo.toString(),
                camarimProperties.getAssistant().getTone(),
                camarimProperties.getStore().getInstagram()
        );
    }
}

