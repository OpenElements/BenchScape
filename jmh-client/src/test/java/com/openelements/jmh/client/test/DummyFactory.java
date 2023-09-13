package com.openelements.jmh.client.test;

import com.openelements.benchscape.common.BenchmarkConfiguration;
import com.openelements.benchscape.common.BenchmarkExecution;
import com.openelements.benchscape.common.BenchmarkExecutionMetadata;
import com.openelements.benchscape.common.BenchmarkExecutionResult;
import com.openelements.benchscape.common.BenchmarkInfrastructure;
import com.openelements.benchscape.common.BenchmarkMeasurementConfiguration;
import com.openelements.benchscape.common.BenchmarkType;
import com.openelements.benchscape.common.BenchmarkUnit;
import java.time.Instant;
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
                "jvmName", "jmhVendor");
    }

    public static BenchmarkExecutionMetadata createBenchmarkExecution() {
        return new BenchmarkExecutionMetadata(Instant.now(), Instant.now(), Instant.now(), Instant.now(), 1, 1);
    }

    public static BenchmarkConfiguration createBenchmarkConfiguration() {
        return new BenchmarkConfiguration(1, 1, 1, TimeUnit.SECONDS, createBenchmarkMeasurementConfiguration(),
                createBenchmarkMeasurementConfiguration());
    }

    public static BenchmarkExecution createBenchmark() {
        return new BenchmarkExecution("test-benchmark", BenchmarkType.THROUGHPUT, createBenchmarkInfrastructure(),
                createBenchmarkConfiguration(), createBenchmarkExecution(), createBenchmarkResult());
    }
}
