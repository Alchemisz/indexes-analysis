package com.example.demo.datastructure.client.dto;

public record ExecuteQueryCommandDTO(
    String query,
    Database database
) {

    public enum Database {
        ORACLE,
        MONGO_DB,
    }

}
