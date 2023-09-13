package com.openelements.jmh.client.jmh;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.runner.Defaults;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * Executes JMH benchmarks and returns the results.
 */
public class JmhExecutor {

    /**
     * Executes the given benchmark classes and returns the results.
     *
     * @param benchmarkClasses the benchmark classes to execute
     * @return the benchmark execution results
     * @throws Exception if an error occurs
     */
    @NonNull
    public static Collection<RunResult> executeClasses(@NonNull final Collection<Class> benchmarkClasses)
            throws Exception {
        Objects.requireNonNull(benchmarkClasses, "benchmarkClasses must not be null");
        return execute(benchmarkClasses.stream().map(Class::getName).toList());
    }

    /**
     * Executes all benchmarks that match the given includes (regex) and returns the results.
     *
     * @param includes the includes (regex) to match
     * @return the benchmark execution results
     * @throws Exception if an error occurs
     */
    @NonNull
    public static Collection<RunResult> execute(@NonNull final Collection<String> includes) throws Exception {
        Objects.requireNonNull(includes, "includes must not be null");
        final OptionsBuilder optionsBuilder = new OptionsBuilder();
        optionsBuilder.getIncludes().addAll(includes);
        return execute(optionsBuilder.build());
    }

    /**
     * Executes all benchmarks on classpath and returns the results.
     *
     * @return the benchmark execution results
     * @throws Exception if an error occurs
     */
    @NonNull
    public static Collection<RunResult> executeAll() throws Exception {
        return execute(Set.of(Defaults.INCLUDE_BENCHMARKS));
    }

    /**
     * Executes benchmarks based on the given options and returns the results.
     *
     * @param options the options to use
     * @return the benchmark execution results
     * @throws Exception if an error occurs
     */
    @NonNull
    private static Collection<RunResult> execute(@NonNull final Options options) throws Exception {
        Objects.requireNonNull(options, "Options must not be null");
        final Runner runner = new Runner(options);
        return runner.run();
    }

}
