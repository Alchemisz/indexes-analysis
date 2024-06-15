package com.example.connection.client.command;


public record SetupConnectionCommand(
    String host,
    Integer port,
    String username,
    String password,
    String database
) {
}
