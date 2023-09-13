package com.openelements.jmh.client;

import com.openelements.benchscape.common.BenchmarkExecution;
import com.openelements.jmh.client.io.RestClient;
import com.openelements.jmh.client.jmh.BenchmarkFactory;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;


@Deprecated
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

        final Set<BenchmarkExecution> results = run.stream()
                .map(result -> BenchmarkFactory.convert(result))
                .collect(Collectors.toSet());

        final RestClient restClient = new RestClient("http://localhost:8080");
        results.stream().forEach(benchmark -> {
            try {
                restClient.post(benchmark);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
