package com.example.connection.application;

import com.example.connection.client.command.SetupConnectionCommand;
import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class SetupMongoDbConnectionService {

    private final MongoConnection mongoConnection;

    void setupConnection(SetupConnectionCommand command) {
        MongoClient mongoClient = createClient(command);
        mongoConnection.setMongoClient(mongoClient);
        mongoConnection.setMongoTemplate(new MongoTemplate(mongoClient, command.getDatabase()));
    }

    private MongoClient createClient(SetupConnectionCommand command) {
        ConnectionString connectionString = prepareConnectionString(command);
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .build();

        return MongoClients.create(mongoClientSettings);
    }

    private static ConnectionString prepareConnectionString(SetupConnectionCommand command) {
        if (!command.getUsername().isEmpty() && !command.getPassword().isEmpty()) {
            return new ConnectionString("mongodb://" + command.getUsername() + ":" + command.getPort() + "@" + command.getHost() + ":" + command.getPort() + "/" + command.getDatabase());
        }
        return new ConnectionString("mongodb://" + command.getHost() + ":" + command.getPort() + "/" + command.getDatabase());
    }

}
