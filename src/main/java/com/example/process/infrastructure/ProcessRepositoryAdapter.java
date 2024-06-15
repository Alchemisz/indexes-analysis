package com.example.process.infrastructure;

import com.example.process.domain.Process;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
class ProcessRepositoryAdapter implements ProcessRepositoryPort {

    private final ProcessRepository processRepository;

    @Override
    public void save(Process process) {
        processRepository.save(process);
    }

    @Override
    public List<Process> findAll() {
        return processRepository.findAll();
    }

    @Override
    public void deleteFinished() {
        processRepository.deleteFinished();
    }

    @Override
    public boolean isAnyRunning() {
        return processRepository.isAnyRunning();
    }

    @Override
    public void deleteById(String id) {
        processRepository.deleteById(id);
    }

}
