package com.example.aiagent.memory;

import java.time.Instant;

public record MemoryEntry(
        String role,   // system | user | agent | tool
        String content,
        Instant timestamp
) {}