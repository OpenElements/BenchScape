package com.openelements.benchscape.jmh.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * Configuration for a benchmark. This configuration is used to configure the benchmark. It is added to a
 * {@link BenchmarkExecution} to store the configuration of the benchmark for the given execution.
 *
 * @param threads                  number of threads to use for the benchmark
 * @param forks                    number of forks to use for the benchmark
 * @param timeout                  timeout for the benchmark
 * @param timeoutUnit              unit of the timeout
 * @param measurementConfiguration configuration for the measurement phase
 * @param warmupConfiguration      configuration for the warmup phase
 */
public record BenchmarkConfiguration(@Min(1) int threads, @Min(0) int forks, @Min(1) long timeout,
                                     @NotNull TimeUnit timeoutUnit,
                                     @NotNull BenchmarkMeasurementConfiguration measurementConfiguration,
                                     @NotNull BenchmarkMeasurementConfiguration warmupConfiguration) {

    public BenchmarkConfiguration {
        if (threads < 1) {
            throw new IllegalArgumentException("threads must be greater than 0");
        }
        if (forks < 0) {
            throw new IllegalArgumentException("forks must be 0 or a positive value");
        }
        if (timeout < 1) {
            throw new IllegalArgumentException("timeout must be greater than 0");
        }
        Objects.requireNonNull(timeoutUnit, "timeoutUnit must not be null");
        Objects.requireNonNull(measurementConfiguration, "measurementConfiguration must not be null");
        Objects.requireNonNull(warmupConfiguration, "warmupConfiguration must not be null");
    }
}
