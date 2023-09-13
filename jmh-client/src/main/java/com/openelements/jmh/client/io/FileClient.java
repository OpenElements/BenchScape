package com.openelements.jmh.client.io;

import com.openelements.benchscape.common.BenchmarkExecution;
import com.openelements.jmh.client.json.BenchmarkJsonFactory;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Objects;

/**
 * Writes benchmark results (as JSON) to the file system. All results will be written as files to a defined directory.
 */
public class FileClient {

    private final static String DEFAULT_DIR = "target/benchmark/";

    private final Path directory;

    /**
     * Creates a new instance that writes the results to the default directory ({@code target/benchmark/}).
     */
    public FileClient() {
        this(DEFAULT_DIR);
    }

    /**
     * Creates a new instance.
     *
     * @param directoryPath the directory to write the results to
     */
    public FileClient(@NonNull final String directoryPath) {
        this(Path.of(directoryPath));
    }

    /**
     * Creates a new instance.
     *
     * @param directory the directory to write the results to
     */
    public FileClient(@NonNull final Path directory) {
        this.directory = Objects.requireNonNull(directory, "directory must not be null");
    }

    /**
     * Writes the benchmark results to the file system.
     *
     * @param benchmarkExecutions the benchmark results
     * @throws IOException if an error occurs
     */
    public void write(@NonNull final Collection<BenchmarkExecution> benchmarkExecutions) throws IOException {
        Objects.requireNonNull(benchmarkExecutions, "benchmarkExecutions must not be null");
        for (final BenchmarkExecution benchmarkExecution : benchmarkExecutions) {
            write(benchmarkExecution);
        }
    }

    /**
     * Writes the benchmark results to the file system.
     *
     * @param benchmarkExecution the benchmark results
     * @throws IOException if an error occurs
     */
    public void write(@NonNull final BenchmarkExecution benchmarkExecution) throws IOException {
        Objects.requireNonNull(benchmarkExecution, "benchmarkExecution must not be null");
        directory.toFile().mkdirs();
        final File file = new File(directory.toFile(), benchmarkExecution.benchmarkName() + ".json");
        final String json = BenchmarkJsonFactory.toJson(benchmarkExecution);
        Files.deleteIfExists(file.toPath());
        Files.writeString(file.toPath(), json, StandardCharsets.UTF_8, StandardOpenOption.WRITE,
                StandardOpenOption.CREATE);
    }
}
