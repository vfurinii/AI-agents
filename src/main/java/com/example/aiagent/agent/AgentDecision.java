package com.example.aiagent.agent;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

public record AgentDecision(
        String action,
        String tool,
        Map<String, Object> params,
        String answer
) {
    public static AgentDecision fromJson(String json) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(json, AgentDecision.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}