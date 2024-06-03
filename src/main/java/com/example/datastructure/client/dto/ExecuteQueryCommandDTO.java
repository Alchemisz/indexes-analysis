package com.example.datastructure.client.dto;

import com.example.datastructure.shared.Database;

public record ExecuteQueryCommandDTO(
    String query,
    Database database
) {
}
