package com.openelements.jmh.client.jmh;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Collection;
import java.util.Objects;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Defaults;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JmhExecutor {

    @NonNull
    public static Collection<RunResult> execute(@NonNull final Collection<Class> benchmarkClasses) throws Exception {
        Objects.requireNonNull(benchmarkClasses, "BenchmarkClasses must not be null");
        final OptionsBuilder optionsBuilder = new OptionsBuilder();
        benchmarkClasses.forEach(cls -> optionsBuilder.getIncludes().add(cls.getName()));
        return execute(optionsBuilder.build());
    }

    @NonNull
    public static Collection<RunResult> executeAll() throws Exception {
        final OptionsBuilder optionsBuilder = new OptionsBuilder();
        optionsBuilder.getIncludes().add(Defaults.INCLUDE_BENCHMARKS);
        return execute(optionsBuilder.build());
    }

    @NonNull
    private static Collection<RunResult> execute(@NonNull final Options options) throws Exception {
        Objects.requireNonNull(options, "Options must not be null");
        final Runner runner = new Runner(options);
        return runner.run();
    }

}
