package com.example.databaseconfiguration.infrastructure;

import com.example.databaseconfiguration.client.dto.DatabaseConfigurationDTO;
import org.springframework.stereotype.Service;

@Service
public class DatabaseConfigurationRepository {

    private DatabaseConfigurationDTO databaseConfigurationDTO;

    public DatabaseConfigurationRepository() {
        this.databaseConfigurationDTO = new DatabaseConfigurationDTO(false, false);
    }

    public void update(DatabaseConfigurationDTO databaseConfigurationDTO) {
        this.databaseConfigurationDTO = databaseConfigurationDTO;
    }

    public DatabaseConfigurationDTO find() {
        return this.databaseConfigurationDTO;
    }
}
