package com.example.datastructure.application;

import com.example.datastructure.infrastructure.oracle.OracleDataStructureRepositoryPort;
import com.example.databaseconfiguration.infrastructure.DatabaseConfigurationRepository;
import com.example.datastructure.client.dto.ExecuteQueryCommandDTO;
import com.example.datastructure.client.dto.QueryExecutionResult;
import com.example.datastructure.infrastructure.history.QueryHistory;
import com.example.datastructure.infrastructure.history.QueryHistoryRepositoryPort;
import com.example.datastructure.infrastructure.mongodb.MongoDbDataStructureRepositoryPort;
import com.example.datastructure.shared.Database;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecuteQueryUseCase {

    private final OracleDataStructureRepositoryPort oracleDataStructureRepositoryPort;
    private final MongoDbDataStructureRepositoryPort mongoDbDataStructureRepositoryPort;
    private final QueryHistoryRepositoryPort queryHistoryRepositoryPort;
    private final DatabaseConfigurationRepository databaseConfigurationRepositoryPort;

    public void execute(ExecuteQueryCommandDTO command) {
        long startTime = System.currentTimeMillis();
        log.info(String.format("Start executing query at time: %s", startTime));

        executeQuery(command);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info(String.format("Finish executing query at time: %s with duration: %s", endTime, duration));

        addHistory(command, calculateExecutionTime(duration));
    }

    private void addHistory(ExecuteQueryCommandDTO command, QueryExecutionResult queryExecutionResult) {
        queryHistoryRepositoryPort.add(
            command.database(),
            new QueryHistory(
                UUID.randomUUID().toString(),
                command.query(),
                queryExecutionResult.toString()
            )
        );
    }

    private void executeQuery(ExecuteQueryCommandDTO command) {
        var databaseConfigurationDTO = databaseConfigurationRepositoryPort.find();
        if (databaseConfigurationDTO.oracleEnabled() && command.database().equals(Database.ORACLE)) {
            oracleDataStructureRepositoryPort.executeQuery(command.query());
        }
        if (databaseConfigurationDTO.mongoDbEnabled() && command.database().equals(Database.MONGO_DB)) {
            mongoDbDataStructureRepositoryPort.execute(command.query());
        }
    }

    private QueryExecutionResult calculateExecutionTime(long duration) {
        long minutes = duration / 60000;
        long seconds = (duration % 60000) / 1000;
        long milliseconds = duration % 1000;

        return new QueryExecutionResult(minutes, seconds, milliseconds);
    }

}
