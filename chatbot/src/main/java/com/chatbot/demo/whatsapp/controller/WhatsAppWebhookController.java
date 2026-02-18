package com.chatbot.demo.whatsapp.controller;

import com.chatbot.demo.whatsapp.dto.WhatsAppWebhook;
import com.chatbot.demo.whatsapp.service.WhatsAppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/whatsapp")
public class WhatsAppWebhookController {

    private static final Logger logger = LoggerFactory.getLogger(WhatsAppWebhookController.class);

    private final WhatsAppService whatsAppService;

    @Value("${whatsapp.verify-token}")
    private String verifyToken;

    public WhatsAppWebhookController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    /**
     * Webhook verification endpoint - Required by WhatsApp
     * GET /webhook/whatsapp?hub.mode=subscribe&hub.verify_token=YOUR_TOKEN&hub.challenge=CHALLENGE
     */
    @GetMapping
    public ResponseEntity<?> verifyWebhook(
            @RequestParam("hub.mode") String mode,
            @RequestParam("hub.verify_token") String token,
            @RequestParam("hub.challenge") String challenge
    ) {
        logger.info("Webhook verification request - mode: {}, token: {}", mode, token);

        if (whatsAppService.verifyWebhook(mode, token, verifyToken)) {
            logger.info("Webhook verified successfully");
            return ResponseEntity.ok(challenge);
        } else {
            logger.warn("Webhook verification failed - invalid token");
            return ResponseEntity.status(403).body("Forbidden");
        }
    }

    /**
     * Webhook endpoint to receive messages from WhatsApp
     * POST /webhook/whatsapp
     */
    @PostMapping
    public ResponseEntity<String> receiveMessage(@RequestBody WhatsAppWebhook webhook) {
        logger.info("Received webhook: {}", webhook);

        try {
            whatsAppService.processWebhook(webhook);
            return ResponseEntity.ok("EVENT_RECEIVED");
        } catch (Exception e) {
            logger.error("Error processing webhook: {}", e.getMessage(), e);
            // Still return 200 to prevent WhatsApp from retrying
            return ResponseEntity.ok("EVENT_RECEIVED");
        }
    }

    /**
     * Manual endpoint to send a test message (for testing purposes)
     * POST /webhook/whatsapp/send
     */
    @PostMapping("/send")
    public ResponseEntity<String> sendTestMessage(
            @RequestParam String to,
            @RequestParam String message
    ) {
        try {
            whatsAppService.sendMessage(to, message);
            return ResponseEntity.ok("Message sent successfully");
        } catch (Exception e) {
            logger.error("Error sending test message: {}", e.getMessage(), e);
            return ResponseEntity.status(500).body("Failed to send message: " + e.getMessage());
        }
    }
}

