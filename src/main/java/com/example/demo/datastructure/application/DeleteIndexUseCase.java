package com.example.demo.datastructure.application;

import com.example.demo.datastructure.client.dto.DeleteIndexCommandDTO;
import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.infrastructure.DataStructureCache;
import com.example.demo.datastructure.infrastructure.DataStructureOracleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DeleteIndexUseCase {

    private final DataStructureCache dataStructureCache;
    private final DataStructureOracleRepositoryPort dataStructureOracleRepositoryPort;

    public void delete(DeleteIndexCommandDTO command) {
        DataStructure dataStructure = dataStructureCache.findByName(command.dataStructureName());
        dataStructure.removeIndex(command.indexName());
        dataStructureOracleRepositoryPort.execute(command.indexName());
    }
}
