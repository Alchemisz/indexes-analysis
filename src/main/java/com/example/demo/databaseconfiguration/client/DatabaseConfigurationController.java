package com.example.demo.databaseconfiguration.client;

import com.example.demo.databaseconfiguration.client.dto.DatabaseConfigurationDTO;
import com.example.demo.databaseconfiguration.infrastructure.DatabaseConfigurationRepositoryPort;
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
