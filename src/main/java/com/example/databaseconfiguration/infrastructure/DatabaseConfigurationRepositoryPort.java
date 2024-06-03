package com.example.databaseconfiguration.infrastructure;

import com.example.databaseconfiguration.client.dto.DatabaseConfigurationDTO;

public interface DatabaseConfigurationRepositoryPort {
    DatabaseConfigurationDTO find();

    void update(DatabaseConfigurationDTO databaseConfigurationDTO);
}
