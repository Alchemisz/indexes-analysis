package com.example.demo.datastructure.application;

import com.example.demo.datastructure.client.dto.ExecuteQueryCommandDTO;
import com.example.demo.datastructure.client.dto.QueryExecutionResult;
import com.example.demo.datastructure.infrastructure.history.QueryHistory;
import com.example.demo.datastructure.infrastructure.history.QueryHistoryRepositoryPort;
import com.example.demo.datastructure.infrastructure.mongodb.DataStructureMongoDbRepositoryPort;
import com.example.demo.datastructure.infrastructure.oracle.DataStructureOracleRepositoryPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExecuteQueryUseCase {

    private final DataStructureOracleRepositoryPort dataStructureOracleRepositoryPort;
    private final DataStructureMongoDbRepositoryPort dataStructureMongoDbRepositoryPort;
    private final QueryHistoryRepositoryPort queryHistoryRepositoryPort;

    public QueryExecutionResult execute(ExecuteQueryCommandDTO command) {
        long startTime = System.currentTimeMillis();
        log.info(String.format("Start executing query at time: %s", startTime));

        executeQuery(command);

        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        log.info(String.format("Finish executing query at time: %s with duration: %s", endTime, duration));

        QueryExecutionResult queryExecutionResult = calculateExecutionTime(duration);

        addHistory(command, queryExecutionResult);

        return queryExecutionResult;
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
        switch (command.database()) {
            case ORACLE -> dataStructureOracleRepositoryPort.executeQuery(command.query());
            case MONGO_DB -> dataStructureMongoDbRepositoryPort.execute(command.query());
            default -> throw new IllegalStateException(String.format("Unhandled database: %s", command.database()));
        }
    }

    private QueryExecutionResult calculateExecutionTime(long duration) {
        long minutes = duration / 60000;
        long seconds = (duration % 60000) / 1000;
        long milliseconds = duration % 1000;

        return new QueryExecutionResult(minutes, seconds, milliseconds);
    }

}
