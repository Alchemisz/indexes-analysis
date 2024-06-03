package com.example.datastructure.infrastructure.mongodb;

import com.example.datastructure.domain.DataStructure;
import com.example.datastructure.infrastructure.CreateIndexParameters;

public interface MongoDbDataStructureRepositoryPort {
    void createDataStructure(DataStructure dataStructure);

    void createIndex(CreateIndexParameters createIndexParameters);

    void removeIndex(String indexName);

    void execute(String query);
}
