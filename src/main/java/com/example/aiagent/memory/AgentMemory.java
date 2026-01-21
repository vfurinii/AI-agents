package com.example.aiagent.memory;

import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;

@Component
public class AgentMemory {

    private static final int MAX_ENTRIES = 20;
    private final LinkedList<MemoryEntry> history = new LinkedList<>();

    public void save(String role, String content) {
        if (history.size() >= MAX_ENTRIES) {
            history.removeFirst();
        }
        history.add(new MemoryEntry(role, content, Instant.now()));
    }

    public List<ChatMessage> toChatMessages() {
        return history.stream()
                .map(e -> new ChatMessage(e.role(), e.content()))
                .toList();
    }
}
