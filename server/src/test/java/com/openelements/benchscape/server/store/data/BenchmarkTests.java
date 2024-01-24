package com.openelements.benchscape.server.store.data;

import java.util.List;
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
        final List<String> tags = List.of();

        //then
        Assertions.assertDoesNotThrow(() -> new Benchmark(id, name, params, tags));
    }

    @Test
    void testNullName() {
        //given
        final UUID id = UUID.randomUUID();
        final String name = null;
        final Map<String, String> params = Map.of();
        final List<String> tags = List.of();

        //then
        Assertions.assertThrows(NullPointerException.class, () -> new Benchmark(id, name, params, tags));
    }

    @Test
    void testNullParams() {
        //given
        final UUID id = UUID.randomUUID();
        final String name = "name";
        final Map<String, String> params = null;
        final List<String> tags = List.of();

        //then
        Assertions.assertThrows(NullPointerException.class, () -> new Benchmark(id, name, params, tags));
    }

    @Test
    void testNullTags() {
        //given
        final UUID id = UUID.randomUUID();
        final String name = "name";
        final Map<String, String> params = Map.of();
        final List<String> tags = null;

        //then
        Assertions.assertThrows(NullPointerException.class, () -> new Benchmark(id, name, params, tags));
    }
}
