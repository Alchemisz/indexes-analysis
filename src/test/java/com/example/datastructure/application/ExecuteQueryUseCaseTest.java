package com.example.datastructure.application;

import com.example.datastructure.client.dto.CreateDataStructureCommandDTO;
import com.example.datastructure.client.dto.ExecuteQueryCommandDTO;
import com.example.datastructure.domain.DataType;
import com.example.datastructure.infrastructure.history.QueryHistory;
import com.example.datastructure.infrastructure.history.QueryHistoryRepositoryPort;
import com.example.datastructure.shared.Database;
import com.example.process.domain.Process;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.example.process.domain.ProcessStatus.FINISHED;
import static com.example.process.domain.ProcessType.QUERY_EXECUTION;
import static org.assertj.core.api.Assertions.assertThat;

class ExecuteQueryUseCaseTest extends AbstractIntegrationTest {

    private static final String DATA_STRUCTURE_NAME = "PERSON";

    @Autowired
    private QueryHistoryRepositoryPort queryHistoryRepositoryPort;
    @Autowired
    private CreateDataStructureUseCase createDataStructureUseCase;
    @Autowired
    private ExecuteQueryUseCase sut;

    @Test
    void execute_query_oracle() {
        //given
        enableOracleDatabase();
        createDataStructure();

        var command = new ExecuteQueryCommandDTO(
            "SELECT * FROM PERSON",
            Database.ORACLE
        );

        //when
        sut.execute(command);

        //them
        List<QueryHistory> histories = queryHistoryRepositoryPort.getByDatabase(Database.ORACLE);
        assertThat(histories)
            .isNotEmpty();
        QueryHistory queryHistory = histories.get(0);
        assertThat(queryHistory.query())
            .isEqualTo(command.query());
        assertThat(queryHistory.time())
            .isNotNull();
        assertThat(queryHistory.id())
            .isNotNull();

        List<Process> processes = processRepositoryPort.findAll();
        Process process = processes.get(0);
        assertThat(process.getType())
            .isEqualTo(QUERY_EXECUTION);
        assertThat(process.getStatus())
            .isEqualTo(FINISHED);

        dropTableOracle(DATA_STRUCTURE_NAME);
    }

    @Test
    void execute_query_mongo() {
        //given
        enableMongoDbDatabase();
        createDataStructure();

        var command = new ExecuteQueryCommandDTO(
            "{count: 'PERSON' }",
            Database.MONGO_DB
        );

        //when
        sut.execute(command);

        //them
        var histories = queryHistoryRepositoryPort.getByDatabase(Database.MONGO_DB);
        assertThat(histories)
            .isNotEmpty();
        QueryHistory queryHistory = histories.get(0);
        assertThat(queryHistory.query())
            .isEqualTo(command.query());
        assertThat(queryHistory.time())
            .isNotNull();
        assertThat(queryHistory.id())
            .isNotNull();

        List<Process> processes = processRepositoryPort.findAll();
        Process process = processes.get(0);
        assertThat(process.getType())
            .isEqualTo(QUERY_EXECUTION);
        assertThat(process.getStatus())
            .isEqualTo(FINISHED);

        dropCollectionMongoDB(DATA_STRUCTURE_NAME);
    }

    private void createDataStructure() {
        createDataStructureUseCase.create(
            new CreateDataStructureCommandDTO(
                new CreateDataStructureCommandDTO.DataStructureDTO(
                    DATA_STRUCTURE_NAME,
                    List.of(
                        new CreateDataStructureCommandDTO.DataStructureElementDTO(
                            "ID",
                            DataType.NUMBER,
                            null,
                            null
                        ),
                        new CreateDataStructureCommandDTO.DataStructureElementDTO(
                            "NAME",
                            DataType.STRING,
                            50,
                            null
                        )
                    )
                ),
                true
            )
        );
    }

}