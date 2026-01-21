package com.example.aiagent.agent;

import com.example.aiagent.llm.LlmClient;
import com.example.aiagent.mappers.ToolMapper;
import com.example.aiagent.memory.AgentMemory;
import com.example.aiagent.tool.AgentTool;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.theokanning.openai.completion.chat.ChatCompletionChoice;
import com.theokanning.openai.completion.chat.ChatCompletionResult;
import com.theokanning.openai.completion.chat.ChatFunction;
import com.theokanning.openai.completion.chat.ChatMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AgentService {

    private final AgentMemory memory;
    private final LlmClient llmClient;
    private final ToolMapper toolMapper;
    private final Map<String, AgentTool> tools;

    public AgentService(
            AgentMemory memory,
            LlmClient llmClient,
            ToolMapper toolMapper,
            List<AgentTool> toolList
    ) {
        this.memory = memory;
        this.llmClient = llmClient;
        this.toolMapper = toolMapper;
        this.tools = toolList.stream()
                .collect(Collectors.toMap(AgentTool::name, t -> t));
    }

    public String handle(String userInput) throws JsonProcessingException {

        memory.save("user", userInput);

        List<ChatMessage> messages = memory.toChatMessages();
        List<ChatFunction> functions = tools.values()
                .stream()
                .map(toolMapper::toFunction)
                .toList();

        ChatCompletionResult result = llmClient.ask(messages, functions);
        ChatCompletionChoice choice = result.getChoices().get(0);
        ChatMessage message = choice.getMessage();

        // 👉 LLM pediu para chamar uma função
        if (message.getFunctionCall() != null) {

            String functionName = message.getFunctionCall().getName();
            String argumentsJson = String.valueOf(message.getFunctionCall().getArguments());

            AgentTool tool = tools.get(functionName);

            Map args = new ObjectMapper()
                    .readValue(argumentsJson, Map.class);

            String observation = tool.execute(args);
            memory.save("tool", observation);

            // loop observe → reason
            return handle(observation);
        }

        memory.save("agent", message.getContent());
        return message.getContent();
    }
}
