package com.example.process.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
public class Process {
    private final String id;
    private final LocalDateTime startTime;
    private LocalDateTime endTime;
    private final ProcessType type;
    private ProcessStatus status;
    private final String content;

    public static Process create(ProcessType processType, String content) {
        return new Process(
            UUID.randomUUID().toString(),
            LocalDateTime.now(),
            null,
            processType,
            ProcessStatus.IN_PROGRESS,
            content
        );
    }

    public void finish() {
        this.status = ProcessStatus.FINISHED;
        this.endTime = LocalDateTime.now();
    }

    public boolean isFinished() {
        return this.status.equals(ProcessStatus.FINISHED);
    }

}
