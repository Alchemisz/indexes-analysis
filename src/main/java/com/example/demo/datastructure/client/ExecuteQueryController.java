package com.example.demo.datastructure.client;

import com.example.demo.datastructure.application.ExecuteQueryUseCase;
import com.example.demo.datastructure.client.dto.ExecuteQueryCommandDTO;
import com.example.demo.datastructure.client.dto.QueryExecutionResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicesrest/execute")
public class ExecuteQueryController {

    private final ExecuteQueryUseCase executeQueryUseCase;

    @PostMapping
    public QueryExecutionResult executeQuery(@RequestBody ExecuteQueryCommandDTO command) {
        return executeQueryUseCase.execute(command);
    }


}
