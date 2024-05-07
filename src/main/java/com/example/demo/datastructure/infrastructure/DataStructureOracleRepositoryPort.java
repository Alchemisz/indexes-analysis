package com.example.demo.datastructure.infrastructure;

import com.example.demo.datastructure.domain.DataStructure;
import com.example.demo.datastructure.infrastructure.oracle.CreateIndexParameters;

public interface DataStructureOracleRepositoryPort {

    void createDataStructure(DataStructure dataStructure);

    void createIndex(CreateIndexParameters createIndexParameters);

    void execute(String indexName);
}
