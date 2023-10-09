package com.openelements.benchscape.jmh.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.openelements.benchscape.jmh.model.BenchmarkType;
import org.junit.jupiter.api.Test;

public class BenchmarkTypeTest {

    @Test
    public void testGetReadableName() {
        BenchmarkType throughput = BenchmarkType.THROUGHPUT;
        assertEquals("Throughput", throughput.getReadableName());
    }

    @Test
    public void testEnumValues() {
        // You can add more enum values here if needed.
        BenchmarkType[] values = BenchmarkType.values();
        assertEquals(1, values.length);
        assertEquals(BenchmarkType.THROUGHPUT, values[0]);
    }
}
