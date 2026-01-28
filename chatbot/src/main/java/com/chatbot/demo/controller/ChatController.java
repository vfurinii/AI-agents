package com.chatbot.demo.controller;

import com.chatbot.demo.service.AgentService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final AgentService agent;

    public ChatController(AgentService agent) {
        this.agent = agent;
    }

    @PostMapping
    public String chat(@RequestBody String message) {
        return agent.chat(message);
    }
}
