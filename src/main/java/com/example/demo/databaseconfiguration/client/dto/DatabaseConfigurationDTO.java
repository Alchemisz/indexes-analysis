package com.example.demo.databaseconfiguration.client.dto;

public record DatabaseConfigurationDTO(
    boolean oracleEnabled,
    boolean mongoDbEnabled
) {
}
