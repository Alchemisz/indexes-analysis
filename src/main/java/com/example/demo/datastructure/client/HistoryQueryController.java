package com.example.demo.datastructure.client;

import com.example.demo.datastructure.infrastructure.history.QueryHistory;
import com.example.demo.datastructure.infrastructure.history.QueryHistoryRepositoryPort;
import com.example.demo.datastructure.shared.Database;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicesrest/history")
public class HistoryQueryController {

    private final QueryHistoryRepositoryPort queryHistoryRepositoryPort;

    @GetMapping("/{database}")
    public List<QueryHistory> executeQuery(@PathVariable Database database) {
        return queryHistoryRepositoryPort.getByDatabase(database);
    }

}
