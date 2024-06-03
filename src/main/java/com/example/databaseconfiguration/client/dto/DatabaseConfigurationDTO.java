package com.example.databaseconfiguration.client.dto;

public record DatabaseConfigurationDTO(
    boolean oracleEnabled,
    boolean mongoDbEnabled
) {
}
