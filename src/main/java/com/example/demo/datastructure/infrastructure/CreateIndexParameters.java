package com.example.demo.datastructure.infrastructure;

import com.example.demo.datastructure.domain.IndexType;

import java.util.Set;

public record CreateIndexParameters(
    String dataStructureName,
    Set<String> dataStructureElementNames,
    String indexName,
    IndexType indexType
) {
}
