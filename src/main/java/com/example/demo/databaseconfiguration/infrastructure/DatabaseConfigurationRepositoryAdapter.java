package com.example.demo.databaseconfiguration.infrastructure;

import com.example.demo.databaseconfiguration.client.dto.DatabaseConfigurationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DatabaseConfigurationRepositoryAdapter implements DatabaseConfigurationRepositoryPort {

    private final DatabaseConfigurationRepository databaseConfigurationRepository;

    @Override
    public DatabaseConfigurationDTO find() {
        return databaseConfigurationRepository.find();
    }

    @Override
    public void update(DatabaseConfigurationDTO databaseConfigurationDTO) {
        databaseConfigurationRepository.update(databaseConfigurationDTO);
    }
}
