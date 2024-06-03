package com.example.databaseconfiguration.client;

import com.example.databaseconfiguration.client.dto.DatabaseConfigurationDTO;
import com.example.databaseconfiguration.infrastructure.DatabaseConfigurationRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/servicesrest/database-configuration")
@RequiredArgsConstructor
public class DatabaseConfigurationController {

    private final DatabaseConfigurationRepositoryPort databaseConfigurationRepositoryPort;

    @GetMapping
    public DatabaseConfigurationDTO findDatabaseConfiguration() {
        return databaseConfigurationRepositoryPort.find();
    }

    @PatchMapping
    public void update(@RequestBody DatabaseConfigurationDTO databaseConfigurationDTO) {
        databaseConfigurationRepositoryPort.update(databaseConfigurationDTO);
    }

}
