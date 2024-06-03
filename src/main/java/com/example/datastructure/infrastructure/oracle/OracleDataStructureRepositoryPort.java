package com.example.datastructure.infrastructure.oracle;

import com.example.datastructure.infrastructure.CreateIndexParameters;
import com.example.datastructure.domain.DataStructure;

public interface OracleDataStructureRepositoryPort {

    void createDataStructure(DataStructure dataStructure);

    void createIndex(CreateIndexParameters createIndexParameters);

    void removeIndex(String indexName);

    void executeQuery(String query);
}
