package com.openelements.benchscape.jmh.model.test;

import com.openelements.benchscape.jmh.model.BenchmarkConfiguration;
import com.openelements.benchscape.jmh.model.BenchmarkMeasurementConfiguration;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BenchmarkConfigurationTest {

    @Test
    void testBadInstantiation() {
        final BenchmarkMeasurementConfiguration measurementConfig = DummyFactory.createBenchmarkMeasurementConfiguration();
        final BenchmarkMeasurementConfiguration warmupConfig = DummyFactory.createBenchmarkMeasurementConfiguration();

        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BenchmarkConfiguration(-1, 1, 1, TimeUnit.SECONDS, measurementConfig, warmupConfig));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BenchmarkConfiguration(0, 1, 1, TimeUnit.SECONDS, measurementConfig, warmupConfig));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BenchmarkConfiguration(-100, 1, 1, TimeUnit.SECONDS, measurementConfig, warmupConfig));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BenchmarkConfiguration(1, -1, 1, TimeUnit.SECONDS, measurementConfig, warmupConfig));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BenchmarkConfiguration(1, -100, 1, TimeUnit.SECONDS, measurementConfig, warmupConfig));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BenchmarkConfiguration(1, 1, -1, TimeUnit.SECONDS, measurementConfig, warmupConfig));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BenchmarkConfiguration(1, 1, 0, TimeUnit.SECONDS, measurementConfig, warmupConfig));
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new BenchmarkConfiguration(1, 1, -100, TimeUnit.SECONDS, measurementConfig, warmupConfig));
        Assertions.assertThrows(NullPointerException.class,
                () -> new BenchmarkConfiguration(1, 1, 1, null, measurementConfig, warmupConfig));
        Assertions.assertThrows(NullPointerException.class,
                () -> new BenchmarkConfiguration(1, 1, 1, TimeUnit.SECONDS, null, warmupConfig));
        Assertions.assertThrows(NullPointerException.class,
                () -> new BenchmarkConfiguration(1, 1, 1, TimeUnit.SECONDS, measurementConfig, null));
    }

    @Test
    void testInstantiation() {
        final BenchmarkMeasurementConfiguration measurementConfig = DummyFactory.createBenchmarkMeasurementConfiguration();
        final BenchmarkMeasurementConfiguration warmupConfig = DummyFactory.createBenchmarkMeasurementConfiguration();

        Assertions.assertDoesNotThrow(
                () -> new BenchmarkConfiguration(1, 1, 1, TimeUnit.SECONDS, measurementConfig, warmupConfig));
    }
}
