package com.openelements.jmh.store.data.test;

import com.openelements.jmh.store.data.Benchmark;
import com.openelements.jmh.store.data.BenchmarkJsonFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

public class BenchmarkJsonFactoryTests {

    @Test
    public void simpleTest() throws IOException {
        //given
        final Path jsonFile = Paths.get(BenchmarkJsonFactoryTests.class.getResource("result.json").getFile());

        //when
        Set<Benchmark> benchmarks = BenchmarkJsonFactory.load(jsonFile);

        //then
        Assertions.assertEquals(2, benchmarks.size());
    }
}
