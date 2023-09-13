package com.openelements.benchscape.common.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.openelements.benchscape.common.BenchmarkExecutionMetadata;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class BenchmarkExecutionMetadataTest {

    @Test
    public void testValidConstruction() {
        Instant startTime = Instant.now();
        Instant warmupTime = Instant.now().plusSeconds(10);
        Instant measurementTime = Instant.now().plusSeconds(20);
        Instant stopTime = Instant.now().plusSeconds(30);
        long warmupOps = 100;
        long measurementOps = 200;

        BenchmarkExecutionMetadata metadata = new BenchmarkExecutionMetadata(startTime, warmupTime, measurementTime,
                stopTime, warmupOps, measurementOps);

        assertEquals(startTime, metadata.startTime());
        assertEquals(warmupTime, metadata.warmupTime());
        assertEquals(measurementTime, metadata.measurementTime());
        assertEquals(stopTime, metadata.stopTime());
        assertEquals(warmupOps, metadata.warmupOps());
        assertEquals(measurementOps, metadata.measurementOps());
    }

    @Test
    public void testNullArguments() {
        assertThrows(NullPointerException.class,
                () -> new BenchmarkExecutionMetadata(null, Instant.now(), Instant.now(), Instant.now(), 100, 200));
        assertThrows(NullPointerException.class,
                () -> new BenchmarkExecutionMetadata(Instant.now(), null, Instant.now(), Instant.now(), 100, 200));
        assertThrows(NullPointerException.class,
                () -> new BenchmarkExecutionMetadata(Instant.now(), Instant.now(), null, Instant.now(), 100, 200));
        assertThrows(NullPointerException.class,
                () -> new BenchmarkExecutionMetadata(Instant.now(), Instant.now(), Instant.now(), null, 100, 200));
    }

    @Test
    public void testInvalidOps() {
        assertThrows(IllegalArgumentException.class,
                () -> new BenchmarkExecutionMetadata(Instant.now(), Instant.now(), Instant.now(), Instant.now(), 0,
                        200));
        assertThrows(IllegalArgumentException.class,
                () -> new BenchmarkExecutionMetadata(Instant.now(), Instant.now(), Instant.now(), Instant.now(), 100,
                        0));
    }
}
