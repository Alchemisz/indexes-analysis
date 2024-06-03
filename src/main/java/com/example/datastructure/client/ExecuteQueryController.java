package com.example.datastructure.client;

import com.example.datastructure.application.ExecuteQueryUseCase;
import com.example.datastructure.client.dto.ExecuteQueryCommandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/servicesrest/execute")
public class ExecuteQueryController {

    private final ExecuteQueryUseCase executeQueryUseCase;

    @PostMapping
    public void executeQuery(@RequestBody ExecuteQueryCommandDTO command) {
        executeQueryUseCase.execute(command);
    }


}
