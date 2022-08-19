package com.openelements.jmh.store.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BenchmarkJsonFactory {

    public static Set<Benchmark> load(final Path jsonFile) throws IOException {
        try (final Reader jsonReader = new FileReader(jsonFile.toFile())) {
            JsonElement jsonElement = JsonParser.parseReader(jsonReader);
            return parse(jsonElement);
        }
    }

    private static Set<Benchmark> parse(final JsonElement jsonElement) {
        if (!jsonElement.isJsonArray()) {
            throw new IllegalArgumentException();
        }
        final Set<Benchmark> result = new HashSet<>();
        jsonElement.getAsJsonArray()
                .forEach(child -> result.add(parseBenchmark(child)));
        return Collections.unmodifiableSet(result);
    }

    private static Benchmark parseBenchmark(final JsonElement jsonElement) {
        final JsonObject params = jsonElement.getAsJsonObject()
                .get("params").getAsJsonObject();
        final String benchmarkName = params.get("benchmark").getAsString();
        final int threads = params.get("threads").getAsInt();
        final int forks = params.get("forks").getAsInt();
        final String mode = params.get("mode").getAsString();
        final String timeunit = params.get("timeUnit").getAsString();
        final long timeout = params.get("timeout").getAsJsonObject().get("time").getAsLong();
        final String timeoutTimeunit = params.get("timeout").getAsJsonObject().get("timeUnit").getAsString();

        final String jmhVersion = params.get("jmhVersion").getAsString();
        final String jvmName = params.get("vmName").getAsString();
        final String jvmVersion = params.get("vmVersion").getAsString();
        final BenchmarkInfrastructure infrastructure = new BenchmarkInfrastructure(jvmVersion, jvmName, jmhVersion);

        final int warmupIterations = params.get("warmup").getAsJsonObject().get("count").getAsInt();
        final int warmupBatchSize = params.get("warmup").getAsJsonObject().get("batchSize").getAsInt();
        final long warmupTime = params.get("warmup").getAsJsonObject().get("timeValue").getAsJsonObject().get("time").getAsLong();
        final String warmupTimeUnit = params.get("warmup").getAsJsonObject().get("timeValue").getAsJsonObject().get("timeUnit").getAsString();
        final BenchmarkMeasurementConfiguration warmupConfiguration = new BenchmarkMeasurementConfiguration(warmupIterations, warmupTime, parseTimeUnit(warmupTimeUnit), warmupBatchSize);

        final int measurementIterations = params.get("warmup").getAsJsonObject().get("count").getAsInt();
        final int measurementBatchSize = params.get("warmup").getAsJsonObject().get("batchSize").getAsInt();
        final long measurementTime = params.get("warmup").getAsJsonObject().get("timeValue").getAsJsonObject().get("time").getAsLong();
        final String measurementTimeUnit = params.get("warmup").getAsJsonObject().get("timeValue").getAsJsonObject().get("timeUnit").getAsString();
        final BenchmarkMeasurementConfiguration measurementConfiguration = new BenchmarkMeasurementConfiguration(measurementIterations, measurementTime, parseTimeUnit(measurementTimeUnit), measurementBatchSize);

        final BenchmarkConfiguration configuration = new BenchmarkConfiguration(threads, forks, parseTimeUnit(measurementTimeUnit), timeout, parseTimeUnit(timeoutTimeunit), measurementConfiguration, warmupConfiguration);

        final BenchmarkResult result = new BenchmarkResult();

        final Benchmark benchmark = new Benchmark(UUID.randomUUID().toString(), benchmarkName, parseType(mode), infrastructure, configuration, result);

        return benchmark;
    }

    private static TimeUnit parseTimeUnit(final String value) {
        return null;
    }

    private static BenchmarkType parseType(final String value) {
        return BenchmarkType.THROUGHPUT;
    }
}
