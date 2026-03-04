package com.camarim.agent.service;

import com.camarim.agent.dto.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CamarimAgentService {

    private final OpenAiService openAiService;
    private final MemoryService memoryService;

    public CamarimAgentService(OpenAiService openAiService, MemoryService memoryService) {
        this.openAiService = openAiService;
        this.memoryService = memoryService;
    }

    public String chat(String sessionId, String userMessage) {
        log.info("Processando mensagem - SessionId: {}, Message: {}", sessionId, userMessage);

        // Salva a mensagem do usuário
        memoryService.saveMessage(sessionId, "user", userMessage);

        // Busca o histórico completo da conversa
        List<ChatMessage> messages = memoryService.getMessages(sessionId);

        // Envia para o OpenAI
        String assistantResponse = openAiService.chat(messages);

        // Salva a resposta do assistente
        memoryService.saveMessage(sessionId, "assistant", assistantResponse);

        log.info("Resposta gerada - SessionId: {}", sessionId);

        return assistantResponse;
    }

    public void clearSession(String sessionId) {
        memoryService.clearSession(sessionId);
        log.info("Sessão limpa: {}", sessionId);
    }

    public int getMessageCount(String sessionId) {
        return memoryService.getMessageCount(sessionId);
    }
}

