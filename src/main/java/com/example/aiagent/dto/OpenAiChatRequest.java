package com.example.aiagent.dto;

import java.util.List;
import java.util.Map;

public class OpenAiChatRequest {
    private String model;
    private List<Map<String, Object>> messages;
    private List<Map<String, Object>> tools;

    public String getModel() { return model; }
    public void setModel(String model) { this.model = model; }
    public List<Map<String, Object>> getMessages() { return messages; }
    public void setMessages(List<Map<String, Object>> messages) { this.messages = messages; }
    public List<Map<String, Object>> getTools() { return tools; }
    public void setTools(List<Map<String, Object>> tools) { this.tools = tools; }
}
