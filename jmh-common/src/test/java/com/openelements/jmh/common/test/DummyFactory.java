package com.openelements.jmh.common.test;

import com.openelements.jmh.common.BenchmarkExecution;
import com.openelements.jmh.common.BenchmarkConfiguration;
import com.openelements.jmh.common.BenchmarkExecutionMetadata;
import com.openelements.jmh.common.BenchmarkInfrastructure;
import com.openelements.jmh.common.BenchmarkMeasurementConfiguration;
import com.openelements.jmh.common.BenchmarkExecutionResult;
import com.openelements.jmh.common.BenchmarkType;
import com.openelements.jmh.common.BenchmarkUnit;
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
        return new BenchmarkInfrastructure("arch", 1, 1, "osName", "osVersion", "jvmVersion", "jvmName", "jmhVendor");
    }

    public static BenchmarkExecutionMetadata createBenchmarkExecution() {
        return new BenchmarkExecutionMetadata(Instant.now(), Instant.now(), Instant.now(), Instant.now(), 1, 1);
    }

    public static BenchmarkConfiguration createBenchmarkConfiguration() {
        return new BenchmarkConfiguration(1, 1, 1, TimeUnit.SECONDS, createBenchmarkMeasurementConfiguration(), createBenchmarkMeasurementConfiguration());
    }

    public static BenchmarkExecution createBenchmark() {
        return new BenchmarkExecution("benchmarkName", BenchmarkType.THROUGHPUT, createBenchmarkInfrastructure(), createBenchmarkConfiguration(), createBenchmarkExecution(), createBenchmarkResult());
    }
}
