package com.example.process.client;

import com.example.process.domain.Process;
import com.example.process.infrastructure.ProcessRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/servicesrest/process")
@RequiredArgsConstructor
public class ProcessController {

    private final ProcessRepositoryPort processRepositoryPort;

    @GetMapping("/all")
    public List<Process> findDatabaseConfiguration() {
        return processRepositoryPort.findAll();
    }

    @DeleteMapping("/finished")
    public void update() {
        processRepositoryPort.deleteFinished();
    }

    @GetMapping("/is-running")
    public boolean isAnyRunning() {
        return processRepositoryPort.isAnyRunning();
    }
}
