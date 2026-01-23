package com.vitorfurini.aiagent.core.tool;

import com.fasterxml.jackson.databind.JsonNode;

public class WeatherTool implements AgentTool {

    public String name() {
        return "getWeather";
    }

    public String description() {
        return "Obtém o clima de uma cidade";
    }

    @Override
    public JsonNode parametersSchema() {
        return null;
    }

    public String execute(JsonNode args) {
        String city = args.get("city").asText();
        return "Clima em " + city + ": 25°C e sol";
    }
}