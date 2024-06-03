package com.example.databaseconfiguration.infrastructure;

import com.example.databaseconfiguration.client.dto.DatabaseConfigurationDTO;
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
