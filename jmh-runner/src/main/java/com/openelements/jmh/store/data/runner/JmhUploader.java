package com.openelements.jmh.store.data.runner;

import com.openelements.jmh.store.data.factory.BenchmarkFactory;
import com.openelements.jmh.store.data.factory.BenchmarkJsonFactory;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JmhUploader {

  private final Set<Class> classes;

  public JmhUploader() {
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

    Set<Benchmark> results = run.stream()
        .map(result -> BenchmarkFactory.convert(result))
        .collect(Collectors.toSet());

    results.stream().forEach(benchmark -> {
      final String json = BenchmarkJsonFactory.toJson(benchmark);

      try {
        final HttpRequest request = HttpRequest.newBuilder()
            .uri(new URI("http://localhost:8080/benchmark"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(json))
            .build();

        final HttpResponse<Void> response = HttpClient
            .newBuilder()
            .build()
            .send(request, HttpResponse.BodyHandlers.discarding());
        System.out.println(response);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });

  }
}
