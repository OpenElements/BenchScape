package com.openelements.benchscape.jmh.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
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
public record BenchmarkExecutionMetadata(@NotNull Instant startTime, @NotNull Instant warmupTime,
                                         @NotNull Instant measurementTime, @NotNull Instant stopTime,
                                         @Min(1) long warmupOps, @Min(1) long measurementOps) {

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
