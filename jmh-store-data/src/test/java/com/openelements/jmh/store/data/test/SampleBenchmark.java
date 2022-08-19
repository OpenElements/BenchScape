package com.openelements.jmh.store.data.test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class SampleBenchmark {

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 3, time = 2)
    @Measurement(iterations = 5, time = 2)
    @Threads(5)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode({Mode.Throughput, Mode.SampleTime})
    public void loadConfiguration(final Blackhole blackhole) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) throws RunnerException {
        final Options opt = new OptionsBuilder().build();
        Collection<RunResult> run = new Runner(opt).run();

        Gson gson = new GsonBuilder().create();
        String jsonString = gson.toJson(run);

        System.out.println(jsonString);
    }

}
