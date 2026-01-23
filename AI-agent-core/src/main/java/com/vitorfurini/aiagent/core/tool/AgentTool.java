package com.vitorfurini.aiagent.core.tool;

import com.fasterxml.jackson.databind.JsonNode;

public interface AgentTool {
    String name();
    String description();
    JsonNode parametersSchema();
    String execute(JsonNode args);
}
