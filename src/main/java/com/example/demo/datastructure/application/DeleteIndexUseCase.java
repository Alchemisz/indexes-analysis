package com.example.demo.datastructure.application;

import com.example.demo.databaseconfiguration.infrastructure.DatabaseConfigurationRepository;
import com.example.demo.datastructure.client.dto.DeleteIndexCommandDTO;
import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.infrastructure.DataStructureCache;
import com.example.demo.datastructure.infrastructure.mongodb.MongoDbDataStructureRepositoryPort;
import com.example.demo.datastructure.infrastructure.oracle.OracleDataStructureRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteIndexUseCase {

    private final DataStructureCache dataStructureCache;
    private final OracleDataStructureRepositoryPort oracleDataStructureRepositoryPort;
    private final MongoDbDataStructureRepositoryPort mongoDbDataStructureRepositoryPort;
    private final DatabaseConfigurationRepository databaseConfigurationRepositoryPort;

    public void delete(DeleteIndexCommandDTO command) {
        DataStructure dataStructure = dataStructureCache.findByName(command.dataStructureName());
        dataStructure.removeIndex(command.indexName());
        deleteIndexFromDatabase(command.indexName());
    }

    private void deleteIndexFromDatabase(String indexName) {
        var databaseConfigurationDTO = databaseConfigurationRepositoryPort.find();
        if (databaseConfigurationDTO.oracleEnabled()) {
            oracleDataStructureRepositoryPort.removeIndex(indexName);
        }
        if (databaseConfigurationDTO.mongoDbEnabled()) {
            mongoDbDataStructureRepositoryPort.removeIndex(indexName);
        }
    }

}
