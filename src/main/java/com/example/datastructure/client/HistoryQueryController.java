package com.example.datastructure.client;

import com.example.datastructure.infrastructure.history.QueryHistory;
import com.example.datastructure.infrastructure.history.QueryHistoryRepositoryPort;
import com.example.datastructure.shared.Database;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicesrest/history")
public class HistoryQueryController {

    private final QueryHistoryRepositoryPort queryHistoryRepositoryPort;

    @GetMapping("/{database}")
    public List<QueryHistory> findHistory(@PathVariable Database database) {
        return queryHistoryRepositoryPort.getByDatabase(database);
    }

    @DeleteMapping("/{database}")
    public void deleteHistory(@PathVariable Database database) {
        queryHistoryRepositoryPort.deleteByDatabase(database);
    }
}
