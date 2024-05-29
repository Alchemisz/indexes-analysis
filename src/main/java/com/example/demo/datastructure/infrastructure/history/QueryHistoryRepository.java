package com.example.demo.datastructure.infrastructure.history;

import com.example.demo.datastructure.shared.Database;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class QueryHistoryRepository {

    private final Map<Database, List<QueryHistory>> historiesByDatabase;


    public QueryHistoryRepository() {
        this.historiesByDatabase = new HashMap<>();
        initRepository();
    }

    void add(Database database, QueryHistory queryHistory) {
        historiesByDatabase.get(database).add(queryHistory);
    }

    List<QueryHistory> getByDatabase(Database database) {
        return historiesByDatabase.get(database);
    }


    private void initRepository() {
        this.historiesByDatabase.putAll(
            Arrays.stream(Database.values())
                .collect(Collectors.toMap(entry -> entry, entry -> new ArrayList<>()))
        );
    }

    public void deleteByDatabase(Database database) {
        this.historiesByDatabase.put(database, new ArrayList<>());
    }
}
