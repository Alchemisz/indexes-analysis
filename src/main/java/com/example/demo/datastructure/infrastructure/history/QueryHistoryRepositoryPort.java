package com.example.demo.datastructure.infrastructure.history;

import com.example.demo.datastructure.shared.Database;

import java.util.List;

public interface QueryHistoryRepositoryPort {
    List<QueryHistory> getByDatabase(Database database);

    void add(Database database, QueryHistory queryHistory);

    void deleteByDatabase(Database database);
}
