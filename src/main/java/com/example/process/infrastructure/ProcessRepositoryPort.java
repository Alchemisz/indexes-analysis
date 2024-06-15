package com.example.process.infrastructure;

import com.example.process.domain.Process;

import java.util.List;

public interface ProcessRepositoryPort {

    void save(Process process);

    List<Process> findAll();

    void deleteFinished();

    boolean isAnyRunning();

    void deleteById(String id);
}
