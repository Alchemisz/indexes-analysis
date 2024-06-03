package com.example.datastructure.infrastructure.history;

public record QueryHistory(
    String id,
    String query,
    String time
) {
}
