package com.example.process.infrastructure;

import com.example.process.domain.Process;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
class ProcessRepository {

    private Map<String, Process> processById;

    public ProcessRepository() {
        this.processById = new HashMap<>();
    }

    void save(Process process) {
        processById.put(process.getId(), process);
    }

    List<Process> findAll() {
        return new ArrayList<>(processById.values());
    }

    void deleteFinished() {
        this.processById = processById.values().stream()
            .filter(Predicate.not(Process::isFinished))
            .collect(Collectors.toMap(Process::getId, entry -> entry));
    }

    public boolean isAnyRunning() {
        return processById.values().stream()
            .anyMatch(Predicate.not(Process::isFinished));
    }

    public void deleteById(String id) {
        processById.remove(id);
    }

    public void deleteAll() {
        this.processById = new HashMap<>();
    }
}
