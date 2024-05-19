package com.example.demo.datastructure.infrastructure.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoIterable;
import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MongoIndexService {


    private final MongoTemplate mongoTemplate;
    private final MongoClient mongoClient;

    Optional<String> findCollectionByIndexNameIndex(String indexName) {
        MongoIterable<String> collectionNames = mongoClient.getDatabase(mongoTemplate.getDb().getName()).listCollectionNames();

        for (String collectionName : collectionNames) {
            var indexes = mongoTemplate.getCollection(collectionName).listIndexes().into(new ArrayList<>());
            for (Document index : indexes) {
                if (indexName.equals(index.getString("name"))) {
                    return Optional.of(collectionName);
                }
            }
        }
        return Optional.empty();
    }
}
