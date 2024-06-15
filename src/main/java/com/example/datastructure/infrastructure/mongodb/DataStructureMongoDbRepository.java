package com.example.datastructure.infrastructure.mongodb;

import com.example.connection.application.MongoConnection;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DataStructureMongoDbRepository {

    private final MongoConnection mongoConnection;

    void createCollection(String collectionName, MongoJsonSchema mongoJsonSchema) {
        mongoConnection.getMongoTemplate().createCollection(collectionName, CollectionOptions.empty().schema(mongoJsonSchema));
    }

    void createIndex(String collectionName, Index index) {
        var indexOps = mongoConnection.getMongoTemplate().indexOps(collectionName);
        indexOps.ensureIndex(index);
    }

    void removeIndex(String collectionName, String indexName) {
        var indexOps = mongoConnection.getMongoTemplate().indexOps(collectionName);
        indexOps.dropIndex(indexName);
    }

    public void execute(String jsonCommand) {
        Document command = Document.parse(jsonCommand);
        mongoConnection.getMongoTemplate().executeCommand(command);
    }
}
