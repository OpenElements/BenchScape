package com.openelements.jmh.store.v2.data;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class MeasurementTests {

    @Test
    void testNullId() {
        //given
        final UUID id = null;
        final Instant timestamp = Instant.now();
        final double value = 0.0d;
        final Double error = 0.0d;
        final Double min = 0.0d;
        final Double max = 5.0d;
        final BenchmarkUnit unit = BenchmarkUnit.MILLISECONDS_PER_OPERATION;

        //then
        Assertions.assertDoesNotThrow(() -> new Measurement(id, timestamp, value, error, min, max, unit));
    }

    @Test
    void testNullTimestamp() {
        //given
        final UUID id = UUID.randomUUID();
        final Instant timestamp = null;
        final double value = 0.0d;
        final Double error = 0.0d;
        final Double min = 0.0d;
        final Double max = 5.0d;
        final BenchmarkUnit unit = BenchmarkUnit.MILLISECONDS_PER_OPERATION;

        //then
        Assertions.assertThrows(NullPointerException.class,
                () -> new Measurement(id, timestamp, value, error, min, max, unit));
    }

    @Test
    void testNullError() {
        //given
        final UUID id = UUID.randomUUID();
        final Instant timestamp = Instant.now();
        final double value = 0.0d;
        final Double error = null;
        final Double min = 0.0d;
        final Double max = 5.0d;
        final BenchmarkUnit unit = BenchmarkUnit.MILLISECONDS_PER_OPERATION;

        //then
        Assertions.assertDoesNotThrow(() -> new Measurement(id, timestamp, value, error, min, max, unit));
    }

    @Test
    void testNullMin() {
        //given
        final UUID id = UUID.randomUUID();
        final Instant timestamp = Instant.now();
        final double value = 0.0d;
        final Double error = 0.0d;
        final Double min = null;
        final Double max = 5.0d;
        final BenchmarkUnit unit = BenchmarkUnit.MILLISECONDS_PER_OPERATION;

        //then
        Assertions.assertDoesNotThrow(() -> new Measurement(id, timestamp, value, error, min, max, unit));
    }

    @Test
    void testNullMax() {
        //given
        final UUID id = UUID.randomUUID();
        final Instant timestamp = Instant.now();
        final double value = 0.0d;
        final Double error = 0.0d;
        final Double min = 0.0d;
        final Double max = null;
        final BenchmarkUnit unit = BenchmarkUnit.MILLISECONDS_PER_OPERATION;

        //then
        Assertions.assertDoesNotThrow(() -> new Measurement(id, timestamp, value, error, min, max, unit));
    }

    @Test
    void testNullUnit() {
        //given
        final UUID id = UUID.randomUUID();
        final Instant timestamp = Instant.now();
        final double value = 0.0d;
        final Double error = 0.0d;
        final Double min = 0.0d;
        final Double max = 5.0d;
        final BenchmarkUnit unit = null;

        //then
        Assertions.assertThrows(NullPointerException.class,
                () -> new Measurement(id, timestamp, value, error, min, max, unit));
    }

    @Test
    void testNegativeValue() {
        //given
        final UUID id = UUID.randomUUID();
        final Instant timestamp = Instant.now();
        final double value = -1.0d;
        final Double error = 0.0d;
        final Double min = 0.0d;
        final Double max = 5.0d;
        final BenchmarkUnit unit = BenchmarkUnit.MILLISECONDS_PER_OPERATION;

        //then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> new Measurement(id, timestamp, value, error, min, max, unit));
    }

    @Test
    void testValueSmallerMin() {
        //given
        final UUID id = UUID.randomUUID();
        final Instant timestamp = Instant.now();
        final double value = 1.0d;
        final Double error = 0.0d;
        final Double min = 2.0d;
        final Double max = 5.0d;
        final BenchmarkUnit unit = BenchmarkUnit.MILLISECONDS_PER_OPERATION;

        //then
        Assertions.assertThrows(IllegalStateException.class,
                () -> new Measurement(id, timestamp, value, error, min, max, unit));
    }

    @Test
    void testValueGreaterMax() {
        //given
        final UUID id = UUID.randomUUID();
        final Instant timestamp = Instant.now();
        final double value = 10.0d;
        final Double error = 0.0d;
        final Double min = 2.0d;
        final Double max = 5.0d;
        final BenchmarkUnit unit = BenchmarkUnit.MILLISECONDS_PER_OPERATION;

        //then
        Assertions.assertThrows(IllegalStateException.class,
                () -> new Measurement(id, timestamp, value, error, min, max, unit));
    }

    @Test
    void testMinGreaterMax() {
        //given
        final UUID id = UUID.randomUUID();
        final Instant timestamp = Instant.now();
        final double value = 10.0d;
        final Double error = 0.0d;
        final Double min = 9.0d;
        final Double max = 5.0d;
        final BenchmarkUnit unit = BenchmarkUnit.MILLISECONDS_PER_OPERATION;

        //then
        Assertions.assertThrows(IllegalStateException.class,
                () -> new Measurement(id, timestamp, value, error, min, max, unit));
    }
}
