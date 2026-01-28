package com.chatbot.demo.service;

import org.springframework.stereotype.Service;

import java.util.ArrayDeque;
import java.util.Deque;

@Service
public class MemoryService {

    private final Deque<String> history = new ArrayDeque<>();
    private final int MAX = 15;

    public void add(String msg) {
        if (history.size() >= MAX) history.pollFirst();
        history.addLast(msg);
    }

    public String context() {
        return String.join("\n", history);
    }
}
