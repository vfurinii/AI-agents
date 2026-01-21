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
        save(role, content, null);
    }

    private void save(String role, String content, String name) {
        if (history.size() >= MAX_ENTRIES) {
            history.removeFirst();
        }
        history.add(new MemoryEntry(role, content, name, Instant.now()));
    }

    public void saveFunctionResult(String functionName, String result) {
        save("function", result, functionName);
    }

    public List<ChatMessage> toChatMessages() {
        return history.stream()
                .map(e -> {
                    if ("function".equals(e.role())) {
                        return new ChatMessage("function", e.content(), e.name());
                    }
                    return new ChatMessage(e.role(), e.content());
                })
                .toList();
    }
}
