package com.openelements.jmh.runner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openelements.jmh.common.Benchmark;
import com.openelements.jmh.runner.factory.BenchmarkFactory;
import com.openelements.jmh.runner.factory.BenchmarkJsonFactory;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;


public class JmhRunner {

  private final Set<Class> classes;

  public JmhRunner() {
    this.classes = new HashSet<>();
  }

  public void addBenchmarkClass(final Class cls) {
    classes.add(cls);
  }

  public void run() throws RunnerException {
    final OptionsBuilder optionsBuilder = new OptionsBuilder();
    classes.forEach(cls -> optionsBuilder.getIncludes().add(cls.getName()));
    final Runner runner = new Runner(optionsBuilder.build());
    Collection<RunResult> run = runner.run();

    final Gson gson = new GsonBuilder().create();
    String jsonString = gson.toJson(run);
    try (final FileWriter fileWriter = new FileWriter("raw-jmh-data.json")) {
      fileWriter.write(jsonString);
    } catch (final IOException e) {
      e.printStackTrace();
    }

    Set<Benchmark> results = run.stream()
        .map(BenchmarkFactory::convert)
        .collect(Collectors.toSet());

    results.stream().forEach(benchmark -> {
      final String json = BenchmarkJsonFactory.toJson(benchmark);
      final String filename = benchmark.id() + ".json";
      try (final FileWriter fileWriter = new FileWriter(filename)) {
        fileWriter.write(json);
      } catch (final IOException e) {
        e.printStackTrace();
      }
    });

  }
}
