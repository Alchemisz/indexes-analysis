package com.example.demo.datastructure.infrastructure.mongodb;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.CollectionOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.schema.MongoJsonSchema;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
class DataStructureMongoDbRepository {

    private final MongoTemplate mongoTemplate;

    void createCollection(String collectionName, MongoJsonSchema mongoJsonSchema) {
        mongoTemplate.createCollection(collectionName, CollectionOptions.empty().schema(mongoJsonSchema));
    }

    void createIndex(String collectionName, Index index) {
        var indexOps = mongoTemplate.indexOps(collectionName);
        indexOps.ensureIndex(index);
    }

    void removeIndex(String collectionName, String indexName) {
        var indexOps = mongoTemplate.indexOps(collectionName);
        indexOps.dropIndex(indexName);
    }
}
