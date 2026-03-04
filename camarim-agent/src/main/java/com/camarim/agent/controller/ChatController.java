package com.camarim.agent.controller;

import com.camarim.agent.dto.ChatRequest;
import com.camarim.agent.dto.ChatResponse;
import com.camarim.agent.service.CamarimAgentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ChatController {

    private final CamarimAgentService camarimAgentService;

    public ChatController(CamarimAgentService camarimAgentService) {
        this.camarimAgentService = camarimAgentService;
    }

    @PostMapping("/chat")
    public ResponseEntity<ChatResponse> chat(@RequestBody ChatRequest request) {
        try {
            String sessionId = request.getSessionId() != null && !request.getSessionId().isEmpty()
                ? request.getSessionId()
                : UUID.randomUUID().toString();

            log.info("Requisição de chat recebida - SessionId: {}", sessionId);

            String response = camarimAgentService.chat(sessionId, request.getMessage());

            return ResponseEntity.ok(new ChatResponse(response, sessionId));

        } catch (Exception e) {
            log.error("Erro ao processar chat", e);
            return ResponseEntity.internalServerError()
                    .body(new ChatResponse("Desculpe, ocorreu um erro. Tente novamente.", null));
        }
    }

    @DeleteMapping("/chat/session/{sessionId}")
    public ResponseEntity<Void> clearSession(@PathVariable String sessionId) {
        log.info("Limpando sessão: {}", sessionId);
        camarimAgentService.clearSession(sessionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/chat/session/{sessionId}/count")
    public ResponseEntity<Integer> getMessageCount(@PathVariable String sessionId) {
        int count = camarimAgentService.getMessageCount(sessionId);
        return ResponseEntity.ok(count);
    }
}

