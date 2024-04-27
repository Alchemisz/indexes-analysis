package com.example.demo.datastructure.client.dto;

import com.example.demo.datastructure.domain.IndexType;

public record CreateIndexCommandDTO(
    String dataStructureName,
    String dataStructureElementName,
    String indexName,
    IndexType indexType
) {
}
