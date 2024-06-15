package com.example.process.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProcessTest {

    private Process process;

    @BeforeEach
    public void setUp() {
        process = Process.create(ProcessType.QUERY_EXECUTION, "Sample content");
    }

    @Test
    void testCreateProcess() {
        assertNotNull(process.getId());
        assertNotNull(process.getStartTime());
        assertNull(process.getEndTime());
        assertEquals(ProcessType.QUERY_EXECUTION, process.getType());
        assertEquals(ProcessStatus.IN_PROGRESS, process.getStatus());
        assertEquals("Sample content", process.getContent());
    }

    @Test
    void testFinishProcess() {
        assertFalse(process.isFinished());

        process.finish();

        assertEquals(ProcessStatus.FINISHED, process.getStatus());
        assertNotNull(process.getEndTime());
        assertTrue(process.isFinished());
    }

    @Test
    void testProcessStatus() {
        assertEquals(ProcessStatus.IN_PROGRESS, process.getStatus());

        process.finish();

        assertEquals(ProcessStatus.FINISHED, process.getStatus());
    }
}
