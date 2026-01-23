package com.example.aiagent.memory;

import java.time.Instant;

public record MemoryEntry(
        String role,
        String content,
        String name,
        Instant timestamp
) {
}