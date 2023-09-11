package com.openelements.benchmark;

import com.openelements.jmh.client.io.FileClient;
import com.openelements.jmh.client.io.RestClient;
import com.openelements.jmh.client.jmh.BenchmarkFactory;
import com.openelements.jmh.client.jmh.JmhExecutor;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@State(Scope.Benchmark)
public class SampleBenchmark {

    @Benchmark
    @Fork(0)
    @Warmup(iterations = 1, time = 1)
    @Threads(2)
    @Measurement(iterations = 2, time = 2)
    @OutputTimeUnit(TimeUnit.MILLISECONDS)
    @BenchmarkMode(Mode.Throughput)
    public void doIt(Blackhole blackhole) throws NoSuchAlgorithmException {
        String value = UUID.randomUUID().toString();
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] encodedhash = digest.digest(value.getBytes(StandardCharsets.UTF_8));
        blackhole.consume(encodedhash);
    }

    public static void main(final String[] args) throws Exception {
        final FileClient fileClient = new FileClient("target/benchmark/");
        final RestClient restClient = new RestClient();
        JmhExecutor.executeAll().stream()
                .map(BenchmarkFactory::convert)
                .forEach(benchmarkExecution -> {
                    try {
                        fileClient.write(benchmarkExecution);
                        restClient.post(benchmarkExecution);
                    } catch (final Exception e) {
                        throw new RuntimeException("Error in posting result", e);
                    }
                });
    }
}
