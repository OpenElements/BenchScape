package com.openelements.jmh.store.data.test;

import com.openelements.jmh.store.data.runner.JmhUploader;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.RunnerException;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@State(Scope.Benchmark)
public class UploaderSample {

    @Benchmark
    @Fork(1)
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

    public static void main(final String[] args) throws RunnerException {
        JmhUploader jmhUploader = new JmhUploader();
        Random random = new Random(System.currentTimeMillis());
        while (true) {
            try {
                jmhUploader.run();
                //Thread.sleep((long) (random.nextDouble() * 5 * 1_000));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
