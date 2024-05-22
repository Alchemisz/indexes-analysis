package com.example.demo.datastructure.client.dto;

import com.example.demo.datastructure.shared.Database;

public record ExecuteQueryCommandDTO(
    String query,
    Database database
) {
}
