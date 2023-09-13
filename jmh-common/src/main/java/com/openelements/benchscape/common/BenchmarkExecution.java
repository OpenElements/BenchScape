package com.openelements.benchscape.common;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Objects;

/**
 * A {@link BenchmarkExecution} is a collection of related data that describes a single benchmark execution.
 *
 * @param benchmarkName  the name of the benchmark
 * @param type           the type of the benchmark
 * @param infrastructure the infrastructure on which the benchmark was executed
 * @param configuration  the configuration of the given benchmark execution
 * @param execution      the execution metadata of the given benchmark execution
 * @param result         the result of the given benchmark execution
 */
public record BenchmarkExecution(@NonNull String benchmarkName, @NonNull BenchmarkType type,
                                 @NonNull BenchmarkInfrastructure infrastructure,
                                 @NonNull BenchmarkConfiguration configuration,
                                 @NonNull BenchmarkExecutionMetadata execution,
                                 @NonNull BenchmarkExecutionResult result) {

    public BenchmarkExecution {
        Objects.requireNonNull(benchmarkName, "benchmarkName must not be null");
        if (benchmarkName.isBlank()) {
            throw new IllegalArgumentException("benchmarkName must not be blank");
        }
        Objects.requireNonNull(type, "type must not be null");
        Objects.requireNonNull(infrastructure, "infrastructure must not be null");
        Objects.requireNonNull(configuration, "configuration must not be null");
        Objects.requireNonNull(execution, "execution must not be null");
        Objects.requireNonNull(result, "result must not be null");
    }
}
