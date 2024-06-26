package com.example.datastructure.application;

import com.example.datastructure.infrastructure.DataStructureCache;
import com.example.databaseconfiguration.infrastructure.DatabaseConfigurationRepositoryPort;
import com.example.datastructure.client.dto.CreateDataStructureCommandDTO;
import com.example.datastructure.domain.DataStructure;
import com.example.datastructure.domain.command.CreateDataStructureCommand;
import com.example.datastructure.infrastructure.mongodb.MongoDbDataStructureRepositoryPort;
import com.example.datastructure.infrastructure.oracle.OracleDataStructureRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.example.datastructure.client.dto.CreateDataStructureCommandDTO.DataStructureDTO;

@Service
@RequiredArgsConstructor
public class CreateDataStructureUseCase {

    private final DataStructureCache dataStructureCache;
    private final OracleDataStructureRepositoryPort oracleDataStructureRepositoryPort;
    private final MongoDbDataStructureRepositoryPort mongoDbDataStructureRepositoryPort;
    private final DatabaseConfigurationRepositoryPort databaseConfigurationRepositoryPort;

    public void create(CreateDataStructureCommandDTO commandDTO) {
        CreateDataStructureCommand command = new CreateDataStructureCommand(
            buildDataStructure(commandDTO.dataStructure())
        );
        DataStructure dataStructure = DataStructure.create(command); //TODO zrobic command port
        dataStructureCache.save(dataStructure);

        if (commandDTO.createInDatabase()) {
            createDataStructureInDatabase(dataStructure);
        }
    }

    private void createDataStructureInDatabase(DataStructure dataStructure) {
        var databaseConfigurationDTO = databaseConfigurationRepositoryPort.find();
        if (databaseConfigurationDTO.oracleEnabled()) {
            oracleDataStructureRepositoryPort.createDataStructure(dataStructure);
        }
        if (databaseConfigurationDTO.mongoDbEnabled()) {
            mongoDbDataStructureRepositoryPort.createDataStructure(dataStructure);
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
                        entry.length(),
                        entry.relatedDataStructure() != null
                            ? buildDataStructure(entry.relatedDataStructure())
                            : null
                    )
                ).toList()
        );
    }
}
