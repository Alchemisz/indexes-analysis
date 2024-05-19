package com.example.demo.datastructure.infrastructure.mongodb;

import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.infrastructure.oracle.CreateIndexParameters;

public interface DataStructureMongoDbRepositoryPort {
    void createDataStructure(DataStructure dataStructure);

    void createIndex(CreateIndexParameters createIndexParameters);

    void removeIndex(String indexName);
}
