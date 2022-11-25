package com.openelements.benchmark;

import com.openelements.jmh.store.data.runner.JmhUploader;
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

  public static void main(final String[] args) throws Exception {
    while (true) {
      JmhUploader jmh = new JmhUploader();
      jmh.addBenchmarkClass(SampleBenchmark.class);
      jmh.addBenchmarkClass(SampleBenchmark2.class);
      jmh.run();
    }
  }
}
