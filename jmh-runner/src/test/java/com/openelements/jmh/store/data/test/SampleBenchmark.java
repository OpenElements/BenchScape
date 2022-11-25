package com.openelements.jmh.store.data.test;

import com.openelements.jmh.store.data.runner.JmhUploader;
import java.util.concurrent.TimeUnit;
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
import org.openjdk.jmh.runner.RunnerException;

@State(Scope.Benchmark)
public class SampleBenchmark {

  //@Benchmark
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
    JmhUploader jmh = new JmhUploader();
    jmh.addBenchmarkClass(SampleBenchmark.class);
    jmh.run();
  }

}
