package com.example.datastructure.client.dto;

public record DeleteIndexCommandDTO(
    String dataStructureName,
    String indexName
) {
}
