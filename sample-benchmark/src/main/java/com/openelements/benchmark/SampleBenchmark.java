package com.openelements.benchmark;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class SampleBenchmark {

    @Benchmark
    @Fork(0)
    @Warmup(iterations = 2, time = 2)
    @Threads(2)
    @Measurement(iterations = 4, time = 3)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    public void doIt(Blackhole blackhole) throws NoSuchAlgorithmException {
        String value = UUID.randomUUID().toString();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        blackhole.consume(encodedhash);
    }
}
