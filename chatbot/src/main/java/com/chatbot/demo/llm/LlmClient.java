package com.chatbot.demo.llm;

import java.util.List;
import java.util.Map;

public interface LlmClient {
    String ask(List<Map<String, String>> messages);
}
