package com.openelements.benchscape.jmh.model.test;

import com.openelements.benchscape.jmh.model.BenchmarkConfiguration;
import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import com.openelements.benchscape.jmh.model.BenchmarkExecutionMetadata;
import com.openelements.benchscape.jmh.model.BenchmarkExecutionResult;
import com.openelements.benchscape.jmh.model.BenchmarkInfrastructure;
import com.openelements.benchscape.jmh.model.BenchmarkType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BenchmarkTest {

    @Test
    void testInstantiation() {
        final BenchmarkInfrastructure infrastructure = DummyFactory.createBenchmarkInfrastructure();
        final BenchmarkConfiguration configuration = DummyFactory.createBenchmarkConfiguration();
        final BenchmarkExecutionMetadata execution = DummyFactory.createBenchmarkExecution();
        final BenchmarkExecutionResult result = DummyFactory.createBenchmarkResult();

        Assertions.assertDoesNotThrow(() -> {
            new BenchmarkExecution("benchmarkName", BenchmarkType.THROUGHPUT, infrastructure, configuration, execution,
                    result);
        });
    }

    @Test
    void testBadInstantiation() {
        final BenchmarkInfrastructure infrastructure = DummyFactory.createBenchmarkInfrastructure();
        final BenchmarkConfiguration configuration = DummyFactory.createBenchmarkConfiguration();
        final BenchmarkExecutionMetadata execution = DummyFactory.createBenchmarkExecution();
        final BenchmarkExecutionResult result = DummyFactory.createBenchmarkResult();
        Assertions.assertThrows(NullPointerException.class, () -> {
            new BenchmarkExecution(null, BenchmarkType.THROUGHPUT, infrastructure, configuration, execution, result);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BenchmarkExecution("", BenchmarkType.THROUGHPUT, infrastructure, configuration, execution, result);
        });
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            new BenchmarkExecution("            ", BenchmarkType.THROUGHPUT, infrastructure, configuration, execution,
                    result);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new BenchmarkExecution("benchmarkName", null, infrastructure, configuration, execution, result);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new BenchmarkExecution("benchmarkName", BenchmarkType.THROUGHPUT, null, configuration, execution, result);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new BenchmarkExecution("benchmarkName", BenchmarkType.THROUGHPUT, infrastructure, null, execution, result);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new BenchmarkExecution("benchmarkName", BenchmarkType.THROUGHPUT, infrastructure, configuration, null,
                    result);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new BenchmarkExecution("benchmarkName", BenchmarkType.THROUGHPUT, infrastructure, configuration, execution,
                    null);
        });
        Assertions.assertThrows(NullPointerException.class, () -> {
            new BenchmarkExecution(null, null, null, null, null, null);
        });
    }
}
