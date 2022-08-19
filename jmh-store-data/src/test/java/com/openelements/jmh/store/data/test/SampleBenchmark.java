package com.openelements.jmh.store.data.test;

import com.openelements.jmh.store.data.runner.JmhRunner;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class SampleBenchmark {

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 2, time = 2)
    @Measurement(iterations = 3, time = 3)
    @Threads(2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    public void loadConfiguration(final Blackhole blackhole) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) throws RunnerException {
        JmhRunner jmhRunner = new JmhRunner();
        jmhRunner.addBenchmarkClass(SampleBenchmark.class);
        jmhRunner.run();
    }

}
