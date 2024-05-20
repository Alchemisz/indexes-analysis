package com.example.demo.datastructure.application;

import com.example.demo.datastructure.client.dto.CreateDataStructureCommandDTO;
import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.domain.command.CreateDataStructureCommand;
import com.example.demo.datastructure.infrastructure.DataStructureCache;
import com.example.demo.datastructure.infrastructure.mongodb.DataStructureMongoDbRepositoryPort;
import com.example.demo.datastructure.infrastructure.oracle.DataStructureOracleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.demo.datastructure.client.dto.CreateDataStructureCommandDTO.DataStructureDTO;

@Service
@RequiredArgsConstructor
public class CreateDataStructureUseCase {

    private final DataStructureCache dataStructureCache;
    private final DataStructureOracleRepositoryPort dataStructureOracleRepositoryPort;
    private final DataStructureMongoDbRepositoryPort dataStructureMongoDbRepositoryPort;

    public void create(CreateDataStructureCommandDTO commandDTO) {
        CreateDataStructureCommand command = new CreateDataStructureCommand(
            buildDataStructure(commandDTO.dataStructure())
        );
        DataStructure dataStructure = DataStructure.create(command); //TODO zrobic command port
        dataStructureCache.save(dataStructure);

        if (commandDTO.createInDatabase()) {
            dataStructureOracleRepositoryPort.createDataStructure(dataStructure);
            dataStructureMongoDbRepositoryPort.createDataStructure(dataStructure);
        }
    }

    private static CreateDataStructureCommand.DataStructure buildDataStructure(DataStructureDTO dataStructureDTO) {
        return new CreateDataStructureCommand.DataStructure(
            dataStructureDTO.name(),
            dataStructureDTO.dataStructureElements().stream()
                .map(entry ->
                    new CreateDataStructureCommand.DataStructureElement(
                        entry.name(),
                        entry.dataType(),
                        entry.relatedDataStructure() != null
                            ? buildDataStructure(entry.relatedDataStructure())
                            : null
                    )
                ).toList()
        );
    }
}
