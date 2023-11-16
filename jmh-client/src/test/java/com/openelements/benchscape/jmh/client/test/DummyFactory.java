package com.openelements.benchscape.jmh.client.test;

import com.openelements.benchscape.jmh.model.BenchmarkConfiguration;
import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import com.openelements.benchscape.jmh.model.BenchmarkExecutionMetadata;
import com.openelements.benchscape.jmh.model.BenchmarkExecutionResult;
import com.openelements.benchscape.jmh.model.BenchmarkGitState;
import com.openelements.benchscape.jmh.model.BenchmarkInfrastructure;
import com.openelements.benchscape.jmh.model.BenchmarkMeasurementConfiguration;
import com.openelements.benchscape.jmh.model.BenchmarkType;
import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import java.time.Instant;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DummyFactory {

    public static BenchmarkExecutionResult createBenchmarkResult() {
        return new BenchmarkExecutionResult(1.0d, 1.0d, 1.0d, 1.0d, BenchmarkUnit.OPERATIONS_PER_MILLISECOND);
    }

    public static BenchmarkMeasurementConfiguration createBenchmarkMeasurementConfiguration() {
        return new BenchmarkMeasurementConfiguration(1, 1, TimeUnit.SECONDS, 1);
    }

    public static BenchmarkInfrastructure createBenchmarkInfrastructure() {
        return new BenchmarkInfrastructure("test-chip-architecture", 4, 1024, "osName", "osVersion", "jvmVersion",
                "jvmName", Map.of(), Map.of(), "jmhVendor");
    }

    public static BenchmarkExecutionMetadata createBenchmarkExecution() {
        return new BenchmarkExecutionMetadata(Instant.now(), Instant.now(), Instant.now(), Instant.now(), 1, 1);
    }

    public static BenchmarkConfiguration createBenchmarkConfiguration() {
        return new BenchmarkConfiguration(1, 1, 1, TimeUnit.SECONDS, createBenchmarkMeasurementConfiguration(),
                createBenchmarkMeasurementConfiguration());
    }

    public static BenchmarkGitState createBenchmarkGitState() {
        return new BenchmarkGitState(null, null, null, Set.of(), false);
    }

    public static BenchmarkExecution createBenchmark() {
        return new BenchmarkExecution("test-benchmark", BenchmarkType.THROUGHPUT, createBenchmarkInfrastructure(),
                createBenchmarkGitState(), createBenchmarkConfiguration(), createBenchmarkExecution(), Map.of(),
                createBenchmarkResult());
    }
}
