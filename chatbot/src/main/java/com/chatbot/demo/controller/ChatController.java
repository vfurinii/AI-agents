package com.chatbot.demo.controller;

import com.chatbot.demo.service.AgentService;
import com.chatbot.demo.service.MemoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final AgentService agent;
    private final MemoryService memory;

    public ChatController(AgentService agent, MemoryService memory) {
        this.agent = agent;
        this.memory = memory;
    }

    @PostMapping
    public String chat(@RequestBody String message) {
        return agent.chat(message);
    }

    @DeleteMapping("/history")
    public String clearHistory() {
        memory.clear();
        return "Conversation history cleared successfully";
    }
}
