package com.example.datastructure.infrastructure.history;

import com.example.datastructure.shared.Database;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class QueryHistoryRepositoryAdapter implements QueryHistoryRepositoryPort {

    private final QueryHistoryRepository queryHistoryRepository;

    @Override
    public List<QueryHistory> getByDatabase(Database database) {
        return queryHistoryRepository.getByDatabase(database);
    }

    @Override
    public void add(Database database, QueryHistory queryHistory) {
        queryHistoryRepository.add(database, queryHistory);
    }

    @Override
    public void deleteByDatabase(Database database) {
        queryHistoryRepository.deleteByDatabase(database);
    }

}
