package com.openelements.benchscape.jmh.model;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Instant;
import java.util.Objects;

/**
 * Metadata about a specific benchmark execution.
 *
 * @param startTime       the time when the benchmark execution started
 * @param warmupTime      the time when the warmup phase started
 * @param measurementTime the time when the measurement phase started
 * @param stopTime        the time when the benchmark execution stopped
 * @param warmupOps       the number of operations executed during the warmup phase
 * @param measurementOps  the number of operations executed during the measurement phase
 */
public record BenchmarkExecutionMetadata(@NonNull Instant startTime, @NonNull Instant warmupTime,
                                         @NonNull Instant measurementTime, @NonNull Instant stopTime,
                                         long warmupOps, long measurementOps) {

    public BenchmarkExecutionMetadata {
        Objects.requireNonNull(startTime, "startTime must not be null");
        Objects.requireNonNull(warmupTime, "warmupTime must not be null");
        Objects.requireNonNull(measurementTime, "measurementTime must not be null");
        Objects.requireNonNull(stopTime, "stopTime must not be null");
        if (warmupOps < 1) {
            throw new IllegalArgumentException("warmupOps must be greater than 0");
        }
        if (measurementOps < 1) {
            throw new IllegalArgumentException("measurementOps must be greater than 0");
        }
    }
}
