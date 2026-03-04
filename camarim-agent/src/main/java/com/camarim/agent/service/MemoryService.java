package com.camarim.agent.service;

import com.camarim.agent.dto.ChatMessage;
import com.camarim.agent.model.Conversation;
import com.camarim.agent.repository.ConversationRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MemoryService {

    private final ConversationRepository conversationRepository;

    public MemoryService(ConversationRepository conversationRepository) {
        this.conversationRepository = conversationRepository;
    }

    public void saveMessage(String sessionId, String role, String content) {
        Conversation conversation = new Conversation();
        conversation.setSessionId(sessionId);
        conversation.setRole(role);
        conversation.setContent(content);

        conversationRepository.save(conversation);
        log.debug("Mensagem salva - SessionId: {}, Role: {}", sessionId, role);
    }

    public List<ChatMessage> getMessages(String sessionId) {
        List<Conversation> conversations = conversationRepository.findBySessionIdOrderByTimestampAsc(sessionId);

        return conversations.stream()
                .map(conv -> new ChatMessage(conv.getRole(), conv.getContent()))
                .collect(Collectors.toList());
    }

    public int getMessageCount(String sessionId) {
        return conversationRepository.countBySessionId(sessionId).intValue();
    }

    @Transactional
    public void clearSession(String sessionId) {
        conversationRepository.deleteBySessionId(sessionId);
        log.info("Sessão limpa: {}", sessionId);
    }
}

