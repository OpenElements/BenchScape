package com.openelements.benchmark;

import java.util.Random;
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

@State(Scope.Benchmark)
public class SampleBenchmark2 {

  @Benchmark
  @Fork(0)
  @Warmup(iterations = 2, time = 2)
  @Threads(2)
  @Measurement(iterations = 4, time = 3)
  @OutputTimeUnit(TimeUnit.MILLISECONDS)
  @BenchmarkMode(Mode.Throughput)
  public void doIt() throws Exception {
    Thread.sleep(new Random(System.currentTimeMillis()).nextLong(1, 10));
  }

}
