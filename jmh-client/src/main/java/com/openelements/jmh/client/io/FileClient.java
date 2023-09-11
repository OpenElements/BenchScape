package com.openelements.jmh.client.io;

import com.openelements.jmh.client.json.BenchmarkJsonFactory;
import com.openelements.jmh.common.BenchmarkExecution;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Objects;

public class FileClient {

    private final Path directory;

    public FileClient(@NonNull final String directoryPath) {
        this(Path.of(directoryPath));
    }

    public FileClient(@NonNull final Path directory) {
        this.directory = Objects.requireNonNull(directory, "directory must not be null");
    }

    public void write(@NonNull final Collection<BenchmarkExecution> benchmarkExecutions) throws Exception {
        Objects.requireNonNull(benchmarkExecutions, "benchmarkExecutions must not be null");
        for (final BenchmarkExecution benchmarkExecution : benchmarkExecutions) {
            write(benchmarkExecution);
        }
    }

    public void write(@NonNull final BenchmarkExecution benchmarkExecution) throws Exception {
        Objects.requireNonNull(benchmarkExecution, "benchmarkExecution must not be null");
        directory.toFile().mkdirs();
        final File file = new File(directory.toFile(), benchmarkExecution.benchmarkName() + ".json");
        final String json = BenchmarkJsonFactory.toJson(benchmarkExecution);
        Files.deleteIfExists(file.toPath());
        Files.writeString(file.toPath(), json, StandardCharsets.UTF_8, StandardOpenOption.WRITE,
                StandardOpenOption.CREATE);
    }
}
