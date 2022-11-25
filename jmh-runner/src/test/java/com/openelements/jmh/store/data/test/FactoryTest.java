package com.openelements.jmh.store.data.test;

import com.openelements.jmh.store.data.factory.BenchmarkFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

public class FactoryTest {

    @Test
    public void simpleTest() throws RunnerException {
        //given
        final OptionsBuilder optionsBuilder = new OptionsBuilder();
        optionsBuilder.getIncludes().add(SampleBenchmark.class.getName());
        final Runner runner = new Runner(optionsBuilder.build());

        //when
        Collection<RunResult> run = new Runner(optionsBuilder.build()).run();
        Set<Benchmark> results = run.stream()
                .map(result -> BenchmarkFactory.convert(result))
                .collect(Collectors.toSet());

        //then
        Assertions.assertEquals(1, results.size());
    }
}
