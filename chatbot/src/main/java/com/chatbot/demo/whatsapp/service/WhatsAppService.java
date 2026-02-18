package com.chatbot.demo.whatsapp.service;

import com.chatbot.demo.service.AgentService;
import com.chatbot.demo.service.MemoryService;
import com.chatbot.demo.whatsapp.dto.WhatsAppMessageRequest;
import com.chatbot.demo.whatsapp.dto.WhatsAppWebhook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class WhatsAppService {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);
    private static final String WHATSAPP_API_URL = "https://graph.facebook.com/v18.0";

    private final AgentService agentService;
    private final MemoryService memoryService;
    private final RestTemplate restTemplate;

    @Value("${whatsapp.phone-number-id}")
    private String phoneNumberId;

    @Value("${whatsapp.access-token}")
    private String accessToken;

    // Store separate conversation memory per WhatsApp user
    private final Map<String, Boolean> userMemoryInitialized = new HashMap<>();

    public WhatsAppService(AgentService agentService, MemoryService memoryService) {
        this.agentService = agentService;
        this.memoryService = memoryService;
        this.restTemplate = new RestTemplate();
    }

    /**
     * Process incoming WhatsApp webhook
     */
    public void processWebhook(WhatsAppWebhook webhook) {
        if (webhook == null || webhook.getEntry() == null || webhook.getEntry().isEmpty()) {
            logger.warn("Received empty webhook");
            return;
        }

        webhook.getEntry().forEach(entry -> {
            if (entry.getChanges() != null) {
                entry.getChanges().forEach(change -> {
                    if (change.getValue() != null && change.getValue().getMessages() != null) {
                        change.getValue().getMessages().forEach(message -> {
                            processIncomingMessage(message, change.getValue().getMetadata());
                        });
                    }
                });
            }
        });
    }

    /**
     * Process a single incoming message
     */
    private void processIncomingMessage(WhatsAppWebhook.Message message, WhatsAppWebhook.Metadata metadata) {
        String userPhone = message.getFrom();
        String messageText = message.getText() != null ? message.getText().getBody() : null;

        if (messageText == null || messageText.trim().isEmpty()) {
            logger.warn("Received message without text from {}", userPhone);
            return;
        }

        logger.info("Received message from {}: {}", userPhone, messageText);

        try {
            // Clear memory for this user if not initialized (optional - for multi-user support)
            // In a production app, you'd maintain separate memories per user

            // Get response from AI agent
            String response = agentService.chat(messageText);

            // Send response back via WhatsApp
            sendMessage(userPhone, response);

            logger.info("Sent response to {}: {}", userPhone, response);

        } catch (Exception e) {
            logger.error("Error processing message from {}: {}", userPhone, e.getMessage(), e);
            sendMessage(userPhone, "Desculpe, ocorreu um erro ao processar sua mensagem. Por favor, tente novamente.");
        }
    }

    /**
     * Send a message via WhatsApp API
     */
    public void sendMessage(String to, String message) {
        try {
            String url = String.format("%s/%s/messages", WHATSAPP_API_URL, phoneNumberId);

            WhatsAppMessageRequest request = new WhatsAppMessageRequest(to, message);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(accessToken);

            HttpEntity<WhatsAppMessageRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class
            );

            if (response.getStatusCode().is2xxSuccessful()) {
                logger.info("Message sent successfully to {}", to);
            } else {
                logger.error("Failed to send message. Status: {}, Body: {}",
                    response.getStatusCode(), response.getBody());
            }

        } catch (Exception e) {
            logger.error("Error sending WhatsApp message to {}: {}", to, e.getMessage(), e);
            throw new RuntimeException("Failed to send WhatsApp message", e);
        }
    }

    /**
     * Verify webhook token for WhatsApp setup
     */
    public boolean verifyWebhook(String mode, String token, String verifyToken) {
        return "subscribe".equals(mode) && verifyToken.equals(token);
    }
}

