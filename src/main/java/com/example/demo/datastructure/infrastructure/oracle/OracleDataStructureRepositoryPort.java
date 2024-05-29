package com.example.demo.datastructure.infrastructure.oracle;

import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.infrastructure.CreateIndexParameters;

public interface OracleDataStructureRepositoryPort {

    void createDataStructure(DataStructure dataStructure);

    void createIndex(CreateIndexParameters createIndexParameters);

    void removeIndex(String indexName);

    void executeQuery(String query);
}
