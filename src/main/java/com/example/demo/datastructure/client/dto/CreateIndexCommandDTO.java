package com.example.demo.datastructure.client.dto;

import com.example.demo.datastructure.domain.IndexType;

import java.util.Set;

public record CreateIndexCommandDTO(
    String dataStructureName,
    Set<String> dataStructureElementNames,
    String indexName,
    IndexType indexType
) {
}
