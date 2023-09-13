package com.openelements.jmh.common.test;

import com.openelements.jmh.common.BenchmarkExecutionResult;
import com.openelements.jmh.common.BenchmarkUnit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BenchmarkExecutionResultTest {

    @Test
    public void testValidConstruction() {
        double value = 300.0;
        Double error = null;
        Double min = 200.0;
        Double max = 400.0;
        BenchmarkUnit unit = BenchmarkUnit.OPERATIONS_PER_SECOND;

        BenchmarkExecutionResult result = new BenchmarkExecutionResult(value, error, min, max, unit);

        assertEquals(value, result.value());
        assertNull(result.error());
        assertEquals(min, result.min());
        assertEquals(max, result.max());
        assertEquals(unit, result.unit());
    }

    @Test
    public void testNullValue() {
        Double value = null;
        Double error = null;
        Double min = 200.0;
        Double max = 400.0;
        BenchmarkUnit unit = BenchmarkUnit.OPERATIONS_PER_SECOND;

        assertThrows(NullPointerException.class, () -> new BenchmarkExecutionResult(value, error, min, max, unit));
    }


    @Test
    public void testNullUnit() {
        double value = 300.0;
        Double error = null;
        Double min = 200.0;
        Double max = 400.0;

        assertThrows(NullPointerException.class, () -> new BenchmarkExecutionResult(value, error, min, max, null));
    }
}
