package com.openelements.benchscape.jmh.client;

import com.openelements.benchscape.jmh.client.io.RestHandler;
import com.openelements.benchscape.jmh.client.jmh.BenchmarkFactory;
import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;


@Deprecated(forRemoval = true)
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

        final RestHandler restHandler = new RestHandler("http://localhost:8080");
        results.stream().forEach(benchmark -> {
            try {
                restHandler.post(benchmark);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }
}
