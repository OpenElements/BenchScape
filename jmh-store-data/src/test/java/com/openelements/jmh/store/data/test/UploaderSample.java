package com.openelements.jmh.store.data.test;

import com.openelements.jmh.store.data.runner.JmhUploader;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.RunnerException;

import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class UploaderSample {

    @Benchmark
    @Fork(1)
    @Warmup(iterations = 2, time = 2)
    @Measurement(iterations = 3, time = 3)
    @Threads(2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    public void doIt() {
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(final String[] args) throws RunnerException {
        JmhUploader jmhUploader = new JmhUploader();
        jmhUploader.addBenchmarkClass(UploaderSample.class);
        jmhUploader.run();
    }
}
