package com.chatbot.demo.service;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MemoryService {

    private final Deque<Map<String, String>> history = new ArrayDeque<>();
    private final int MAX = 10; // Max conversation turns (10 pairs = 20 messages)

    public void addUserMessage(String message) {
        addMessage("user", message);
    }

    public void addAssistantMessage(String message) {
        addMessage("assistant", message);
    }

    private void addMessage(String role, String content) {
        if (history.size() >= MAX * 2) { // Keep MAX pairs
            history.pollFirst();
        }
        history.addLast(Map.of("role", role, "content", content));
    }

    public List<Map<String, String>> getMessages() {
        return history.stream().collect(Collectors.toList());
    }

    public void clear() {
        history.clear();
    }
}
