package com.example.demo.datastructure.application;

import com.example.demo.datastructure.client.dto.CreateIndexCommandDTO;
import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.infrastructure.DataStructureCache;
import com.example.demo.datastructure.infrastructure.DataStructureOracleRepositoryPort;
import com.example.demo.datastructure.infrastructure.mongodb.DataStructureMongoDbRepositoryPort;
import com.example.demo.datastructure.infrastructure.oracle.CreateIndexParameters;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateIndexUseCase {

    private final DataStructureCache dataStructureCache;
    private final DataStructureOracleRepositoryPort dataStructureOracleRepositoryPort;
    private final DataStructureMongoDbRepositoryPort dataStructureMongoDbRepositoryPort;

    public void createForDataStructure(CreateIndexCommandDTO commandDTO) {
        //TODO zrobić clean code póki się da005
        //TODO zrobić obsługe indeksów dla wielu pól
        DataStructure dataStructure = dataStructureCache.findByName(commandDTO.dataStructureName());
        dataStructure.createIndex(commandDTO);
        dataStructureCache.save(dataStructure);

        CreateIndexParameters createIndexParameters = new CreateIndexParameters(
            commandDTO.dataStructureName(),
            commandDTO.dataStructureElementNames(),
            commandDTO.indexName(),
            commandDTO.indexType()
        );

        dataStructureOracleRepositoryPort.createIndex(createIndexParameters);
        dataStructureMongoDbRepositoryPort.createIndex(createIndexParameters);
    }

}
