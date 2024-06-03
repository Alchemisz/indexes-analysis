package com.example.datastructure.client.dto;

import com.example.datastructure.domain.IndexType;

import java.util.Set;

public record CreateIndexCommandDTO(
    String dataStructureName,
    Set<String> dataStructureElementNames,
    String indexName,
    IndexType indexType
) {
}
