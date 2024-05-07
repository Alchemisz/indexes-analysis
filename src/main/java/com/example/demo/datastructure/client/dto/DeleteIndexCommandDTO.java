package com.example.demo.datastructure.client.dto;

public record DeleteIndexCommandDTO(
    String dataStructureName,
    String indexName
) {
}
