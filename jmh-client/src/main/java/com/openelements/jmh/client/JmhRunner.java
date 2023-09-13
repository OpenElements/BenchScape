package com.openelements.jmh.client;

import com.openelements.jmh.client.io.FileClient;
import com.openelements.jmh.client.io.RestClient;
import com.openelements.jmh.client.jmh.BenchmarkFactory;
import com.openelements.jmh.client.jmh.JmhExecutor;
import com.openelements.jmh.common.BenchmarkExecution;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.nio.file.Path;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import org.openjdk.jmh.results.RunResult;

/**
 * Runs the JMH benchmarks and handles the results by posting them to a server and/or writing them to disc.
 *
 * @see FileClient
 * @see RestClient
 * @see JmhExecutor
 */
public class JmhRunner {

    private final boolean uploadResults;

    private final boolean writeResults;

    private final Path resultsPath;

    private final String uploadBaseUrl;

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
        this(uploadResults, writeResults, null, null, null);
    }

    /**
     * Constructor.
     *
     * @param uploadResults whether to post the results to a server
     * @param writeResults  whether to write the results to disc
     * @param resultsPath   the path of  the directory to write the results to. If null, the default directory is used
     * @param uploadBaseUrl the base URL of the server to post the results to. If null, the default server is used
     * @param includes      the names of the benchmarks to run. If empty, all benchmarks are run
     */
    public JmhRunner(boolean uploadResults, boolean writeResults, @Nullable Path resultsPath,
            @Nullable String uploadBaseUrl,
            @Nullable Collection<String> includes) {
        this.uploadResults = uploadResults;
        this.writeResults = writeResults;
        this.resultsPath = resultsPath;
        this.uploadBaseUrl = uploadBaseUrl;
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
            final RestClient restClient;
            if (uploadBaseUrl == null) {
                restClient = new RestClient();
            } else {
                restClient = new RestClient(uploadBaseUrl);
            }
            restClient.post(benchmarkExecutions);
        }

        if (writeResults) {
            final FileClient fileClient;
            if (resultsPath == null) {
                fileClient = new FileClient();
            } else {
                fileClient = new FileClient(resultsPath);
            }
            fileClient.write(benchmarkExecutions);
        }
    }
}
