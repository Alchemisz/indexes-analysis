package com.example.connection.application;

import com.example.connection.client.command.SetupConnectionCommand;
import com.example.datastructure.shared.Database;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConnectionFacade {

    private final SetupMongoDbConnectionService setupMongoDBConnectionService;
    private final SetupOracleConnectionService setupOracleConnectionService;
    private final TestConnectionService testConnectionService;

    public void connectToDatabase(SetupConnectionCommand command, Database database) {
        switch (database) {
            case ORACLE -> setupOracleConnectionService.setupConnection(command);
            case MONGO_DB -> setupMongoDBConnectionService.setupConnection(command);
        }
    }

    public boolean testDatabaseConnection(String databaseName, Database database) {
        return switch (database) {
            case ORACLE -> testConnectionService.testOracleConnection();
            case MONGO_DB -> testConnectionService.testMongoDbConnection(databaseName);
        };
    }

}
