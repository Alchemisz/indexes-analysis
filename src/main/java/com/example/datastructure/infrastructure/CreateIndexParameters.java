package com.example.datastructure.infrastructure;

import com.example.datastructure.domain.IndexType;

import java.util.Set;

public record CreateIndexParameters(
    String dataStructureName,
    Set<String> dataStructureElementNames,
    String indexName,
    IndexType indexType
) {
}
