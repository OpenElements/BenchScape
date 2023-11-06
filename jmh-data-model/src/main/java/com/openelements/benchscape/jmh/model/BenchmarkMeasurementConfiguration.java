package com.openelements.benchscape.jmh.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * The configuration of a benchmark measurement. Such configuration is provided for the warmup and the measurement phase
 * of the benchmark.
 *
 * @param iterations number of iterations to execute
 * @param time       time to execute the benchmark in the given time unit
 * @param timeUnit   unit of the time
 * @param batchSize  number of operations to execute in a batch
 */
public record BenchmarkMeasurementConfiguration(@Min(1) int iterations, @Min(1) long time, @NotNull TimeUnit timeUnit,
                                                @Min(1) int batchSize) {

    public BenchmarkMeasurementConfiguration {
        if (iterations < 1) {
            throw new IllegalArgumentException("iterations must be greater than 0");
        }
        if (time < 1) {
            throw new IllegalArgumentException("time must be greater than 0");
        }
        Objects.requireNonNull(timeUnit, "timeUnit must not be null");
        if (batchSize < 1) {
            throw new IllegalArgumentException("batchSize must be greater than 0");
        }
    }
}
