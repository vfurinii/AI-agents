package com.example.aiagent.tool;

import com.example.aiagent.agent.OpenAiService;
import com.example.aiagent.dto.OpenAiChatRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class WeatherTool implements AgentTool {

    public String name() { return "get_weather"; }
    public String description() { return "Consulta clima"; }

    @Autowired
    public OpenAiService openAiService;

    public String execute(Map<String, Object> params) {
        String city = (String) params.get("city");
        String prompt = "Qual é a temperatura atual em " + city + "?";
        OpenAiChatRequest request = new OpenAiChatRequest();
        request.setModel("gpt-4o");
        request.setMessages(List.of(Map.of(
                "role", "user",
                "content", prompt
        )));
        return openAiService.sendChatRequest(request);
    }
}