package com.openelements.benchscape.jmh.client;

import com.openelements.benchscape.jmh.client.io.FileHandler;
import com.openelements.benchscape.jmh.client.io.RestHandler;
import com.openelements.benchscape.jmh.client.io.RestHandlerConfig;
import com.openelements.benchscape.jmh.client.jmh.BenchmarkFactory;
import com.openelements.benchscape.jmh.client.jmh.JmhExecutor;
import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import org.openjdk.jmh.results.RunResult;

/**
 * Runs the JMH benchmarks and handles the results by posting them to a server and/or writing them to disc.
 *
 * @see FileHandler
 * @see RestHandler
 * @see JmhExecutor
 */
public class JmhRunner {

    private final boolean uploadResults;

    private final boolean writeResults;

    private final Path resultsPath;

    private final RestHandlerConfig restHandlerConfig;

    private final Collection<String> includes;

    /**
     * Constructor that uses defaults for the directory path and upload base URL and includes all benchmarks.
     */
    public JmhRunner() {
        this(true, true);
    }

    /**
     * Constructor that uses defaults for the directory path and upload base URL.
     *
     * @param uploadResults whether to post the results to a server
     * @param writeResults  whether to write the results to disc
     */
    public JmhRunner(boolean uploadResults, boolean writeResults) {
        this(uploadResults, writeResults, null, new RestHandlerConfig(), null);
    }

    /**
     * Constructor.
     *
     * @param uploadResults     whether to post the results to a server
     * @param writeResults      whether to write the results to disc
     * @param resultsPath       the path of  the directory to write the results to. If null, the default directory is
     *                          used
     * @param restHandlerConfig TODO
     * @param includes          the names of the benchmarks to run. If empty, all benchmarks are run
     */
    public JmhRunner(boolean uploadResults, boolean writeResults, @Nullable Path resultsPath,
            @Nullable RestHandlerConfig restHandlerConfig,
            @Nullable Collection<String> includes) {
        this.uploadResults = uploadResults;
        this.writeResults = writeResults;
        this.resultsPath = resultsPath;
        this.restHandlerConfig = Objects.requireNonNull(restHandlerConfig, "restHandlerConfig must not be null");
        if (includes == null) {
            this.includes = Set.of();
        } else {
            this.includes = Collections.unmodifiableCollection(includes);
        }
    }

    /**
     * Runs the JMH benchmarks and handles the results by posting them to a server and/or writing them to disc.
     *
     * @throws Exception if an error occurs
     */
    public void execute() throws Exception {
        final Collection<RunResult> runResults;
        if (includes.isEmpty()) {
            runResults = JmhExecutor.executeAll();
        } else {
            runResults = JmhExecutor.execute(includes);
        }

        final Collection<BenchmarkExecution> benchmarkExecutions = BenchmarkFactory.convert(runResults);

        if (uploadResults) {
            final RestHandler restHandler = new RestHandler(restHandlerConfig);
            restHandler.post(benchmarkExecutions);
        }

        if (writeResults) {
            final FileHandler fileHandler;
            if (resultsPath == null) {
                fileHandler = new FileHandler();
            } else {
                fileHandler = new FileHandler(resultsPath);
            }
            fileHandler.write(benchmarkExecutions);
        }
    }

    public static void main(String[] args) throws Exception {
        new JmhRunner().execute();
    }
}
