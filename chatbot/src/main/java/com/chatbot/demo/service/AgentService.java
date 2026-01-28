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

        memory.add("User: " + userMessage);

        String prompt = """
        You are a customizable chatbot.

        Conversation so far:
        %s

        Answer naturally:
        User: %s
        """.formatted(memory.context(), userMessage);

        String response = llm.ask(prompt);

        memory.add("Assistant: " + response);

        return response;
    }
}
