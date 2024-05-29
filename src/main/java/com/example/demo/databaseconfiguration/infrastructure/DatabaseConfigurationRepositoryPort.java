package com.example.demo.databaseconfiguration.infrastructure;

import com.example.demo.databaseconfiguration.client.dto.DatabaseConfigurationDTO;

public interface DatabaseConfigurationRepositoryPort {
    DatabaseConfigurationDTO find();

    void update(DatabaseConfigurationDTO databaseConfigurationDTO);
}
