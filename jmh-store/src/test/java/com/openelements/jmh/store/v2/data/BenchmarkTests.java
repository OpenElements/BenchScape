package com.openelements.jmh.store.v2.data;

import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BenchmarkTests {

    @Test
    void testNullId() {
        //given
        final UUID id = null;
        final String name = "name";
        final Map<String, String> params = Map.of();

        //then
        Assertions.assertDoesNotThrow(() -> new Benchmark(id, name, params));
    }

    @Test
    void testNullName() {
        //given
        final UUID id = UUID.randomUUID();
        final String name = null;
        final Map<String, String> params = Map.of();

        //then
        Assertions.assertThrows(NullPointerException.class, () -> new Benchmark(id, name, params));
    }

    @Test
    void testNullParams() {
        //given
        final UUID id = UUID.randomUUID();
        final String name = "name";
        final Map<String, String> params = null;

        //then
        Assertions.assertThrows(NullPointerException.class, () -> new Benchmark(id, name, params));
    }
}
