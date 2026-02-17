package com.chatbot.demo.service;

import com.chatbot.demo.domain.Message;
import com.chatbot.demo.domain.MessageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemoryService {

    private final MessageRepository messageRepository;
    private final int MAX = 10; // Max conversation turns (10 pairs = 20 messages)
    private final String SESSION_ID = "default"; // Single session for now

    public MemoryService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public void addUserMessage(String message) {
        addMessage("user", message);
    }

    public void addAssistantMessage(String message) {
        addMessage("assistant", message);
    }

    private void addMessage(String role, String content) {
        // Save message to database
        Message message = new Message(role, content);
        message.setSessionId(SESSION_ID);
        messageRepository.save(message);

        // Trim old messages if exceeding MAX
        List<Message> allMessages = messageRepository.findBySessionIdOrderByTimestampAsc(SESSION_ID);
        if (allMessages.size() > MAX * 2) {
            // Delete oldest messages
            int toDelete = allMessages.size() - (MAX * 2);
            List<Message> messagesToDelete = allMessages.subList(0, toDelete);
            messageRepository.deleteAll(messagesToDelete);
        }
    }

    public List<Map<String, String>> getMessages() {
        return messageRepository.findBySessionIdOrderByTimestampAsc(SESSION_ID)
                .stream()
                .map(msg -> Map.of("role", msg.getRole(), "content", msg.getContent()))
                .collect(Collectors.toList());
    }

    @Transactional
    public void clear() {
        messageRepository.deleteBySessionId(SESSION_ID);
    }
}
