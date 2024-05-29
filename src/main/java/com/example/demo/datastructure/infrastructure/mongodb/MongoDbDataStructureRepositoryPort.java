package com.example.demo.datastructure.infrastructure.mongodb;

import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.infrastructure.CreateIndexParameters;

public interface MongoDbDataStructureRepositoryPort {
    void createDataStructure(DataStructure dataStructure);

    void createIndex(CreateIndexParameters createIndexParameters);

    void removeIndex(String indexName);

    void execute(String query);
}
