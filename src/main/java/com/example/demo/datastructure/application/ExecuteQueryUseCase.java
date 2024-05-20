package com.example.demo.datastructure.application;

import com.example.demo.datastructure.client.dto.ExecuteQueryCommandDTO;
import com.example.demo.datastructure.infrastructure.mongodb.DataStructureMongoDbRepositoryPort;
import com.example.demo.datastructure.infrastructure.oracle.DataStructureOracleRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ExecuteQueryUseCase {

    private final DataStructureOracleRepositoryPort dataStructureOracleRepositoryPort;
    private final DataStructureMongoDbRepositoryPort dataStructureMongoDbRepositoryPort;

    public void execute(ExecuteQueryCommandDTO command) {
        executeQuery(command);
    }

    private void executeQuery(ExecuteQueryCommandDTO command) {
        switch (command.database()) {
            case ORACLE -> dataStructureOracleRepositoryPort.execute(command.query());
            case MONGO_DB -> dataStructureMongoDbRepositoryPort.execute(command.query());
            default -> throw new IllegalStateException(String.format("Unhandled database: %s", command.database()));
        }
    }

}
