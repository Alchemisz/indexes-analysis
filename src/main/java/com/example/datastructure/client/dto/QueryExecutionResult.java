package com.example.datastructure.client.dto;

public record QueryExecutionResult(
    long minutes,
    long seconds,
    long milliseconds
) {

    @Override
    public String toString() {
        return String.format("%s:%s:%s", minutes, seconds, milliseconds);
    }
}