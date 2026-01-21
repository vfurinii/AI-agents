package com.example.aiagent.mappers;

import com.example.aiagent.parameters.GetWeatherParameters;
import com.example.aiagent.tool.AgentTool;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatFunction;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ToolMapper {

    private final ObjectMapper mapper = new ObjectMapper();

    public ChatFunction toFunction(AgentTool tool) {

        Map<String, Object> schema = Map.of(
                "type", "object",
                "properties", Map.of(
                        "city", Map.of("type", "string")
                ),
                "required", List.of("city")
        );

        ChatFunction function = new ChatFunction();
        function.setName(tool.name());
        function.setDescription(tool.description());
        function.setParametersClass(GetWeatherParameters.class);

        return function;
    }
}
