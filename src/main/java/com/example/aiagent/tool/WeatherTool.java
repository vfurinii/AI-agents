package com.example.aiagent.tool;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class WeatherTool implements AgentTool {

    public String name() { return "get_weather"; }
    public String description() { return "Consulta clima"; }


    public String execute(Map<String, Object> params) {
        return "Clima em " + params.get("city") + " é 25°C";
    }
}