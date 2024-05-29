package com.example.demo.datastructure.application;

import com.example.demo.databaseconfiguration.infrastructure.DatabaseConfigurationRepository;
import com.example.demo.datastructure.client.dto.CreateIndexCommandDTO;
import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.infrastructure.CreateIndexParameters;
import com.example.demo.datastructure.infrastructure.DataStructureCache;
import com.example.demo.datastructure.infrastructure.mongodb.MongoDbDataStructureRepositoryPort;
import com.example.demo.datastructure.infrastructure.oracle.OracleDataStructureRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateIndexUseCase {

    private final DataStructureCache dataStructureCache;
    private final OracleDataStructureRepositoryPort oracleDataStructureRepositoryPort;
    private final MongoDbDataStructureRepositoryPort mongoDbDataStructureRepositoryPort;
    private final DatabaseConfigurationRepository databaseConfigurationRepositoryPort;

    public void createForDataStructure(CreateIndexCommandDTO commandDTO) {
        DataStructure dataStructure = dataStructureCache.findByName(commandDTO.dataStructureName());
        dataStructure.createIndex(commandDTO);
        dataStructureCache.save(dataStructure);

        CreateIndexParameters createIndexParameters = new CreateIndexParameters(
            commandDTO.dataStructureName(),
            commandDTO.dataStructureElementNames(),
            commandDTO.indexName(),
            commandDTO.indexType()
        );

        createIndexInDatabase(createIndexParameters);
    }

    private void createIndexInDatabase(CreateIndexParameters createIndexParameters) {
        var databaseConfigurationDTO = databaseConfigurationRepositoryPort.find();
        if (databaseConfigurationDTO.oracleEnabled()) {
            oracleDataStructureRepositoryPort.createIndex(createIndexParameters);
        }
        if (databaseConfigurationDTO.mongoDbEnabled()) {
            mongoDbDataStructureRepositoryPort.createIndex(createIndexParameters);
        }
    }


}
