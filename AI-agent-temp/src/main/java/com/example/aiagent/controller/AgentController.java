package com.example.aiagent.controller;

import com.example.aiagent.agent.AgentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agent")
public class AgentController {
    private final AgentService agentService;
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }
    @PostMapping
    public String ask(@RequestBody String input) throws JsonProcessingException {
        return agentService.handle(input);
    }
}