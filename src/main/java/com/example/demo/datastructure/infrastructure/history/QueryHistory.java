package com.example.demo.datastructure.infrastructure.history;

public record QueryHistory(
    String id,
    String query,
    String time
) {
}
