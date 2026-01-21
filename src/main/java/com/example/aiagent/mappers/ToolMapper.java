package com.example.aiagent.mappers;

import com.example.aiagent.tool.AgentTool;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatFunction;
import org.springframework.stereotype.Component;

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
                "required", new String[]{"city"}
        );

        JsonNode parametersNode = mapper.valueToTree(schema);

        ChatFunction function = new ChatFunction();
        function.setName(tool.name());
        function.setDescription(tool.description());
        function.setParametersClass(parametersNode.getClass());

        return function;
    }
}
