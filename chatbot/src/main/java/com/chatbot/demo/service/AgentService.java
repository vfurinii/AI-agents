package com.chatbot.demo.service;

import com.chatbot.demo.llm.LlmClient;
import org.springframework.stereotype.Service;

@Service
public class AgentService {

    private final LlmClient llm;
    private final MemoryService memory;

    public AgentService(LlmClient llm, MemoryService memory) {
        this.llm = llm;
        this.memory = memory;
    }

    public String chat(String userMessage) {

        // Add user message to memory
        memory.addUserMessage(userMessage);

        // Send all conversation history to LLM
        String response = llm.ask(memory.getMessages());

        // Add assistant response to memory
        memory.addAssistantMessage(response);

        return response;
    }
}
