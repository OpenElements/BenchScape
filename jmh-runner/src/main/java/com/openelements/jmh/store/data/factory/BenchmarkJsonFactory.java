package com.openelements.jmh.store.data.factory;

import com.google.gson.*;
import com.openelements.jmh.store.data.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.nio.file.Path;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BenchmarkJsonFactory {

    public static Set<Benchmark> loadRawJmhJsonResult(final Path jsonFile) throws IOException {
        try (final Reader jsonReader = new FileReader(jsonFile.toFile())) {
            JsonElement jsonElement = JsonParser.parseReader(jsonReader);
            return parse(jsonElement);
        }
    }

    public static String toJson(final Set<Benchmark> benchmarks) {
        final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantConverter())
                .serializeSpecialFloatingPointValues()
                .create();
        return gson.toJson(benchmarks);
    }

    public static String toJson(final Benchmark benchmark) {
        final Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantConverter())
                .serializeSpecialFloatingPointValues()
                .create();
        return gson.toJson(benchmark);
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
        final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        final String arch = osBean.getArch();
        final int availableProcessors = osBean.getAvailableProcessors();
        final String osVersion = osBean.getVersion();
        final String osName = osBean.getName();
        final BenchmarkInfrastructure infrastructure = new BenchmarkInfrastructure(arch, availableProcessors, -1L, osName, osVersion, jvmVersion, jvmName, jmhVersion);

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

        final BenchmarkConfiguration configuration = new BenchmarkConfiguration(threads, forks, parseTimeUnit(timeunit), timeout, parseTimeUnit(timeoutTimeunit), measurementConfiguration, warmupConfiguration);

        final Benchmark benchmark = new Benchmark(UUID.randomUUID().toString(), benchmarkName, parseType(mode), infrastructure, configuration, null, null);

        return benchmark;
    }

    private static TimeUnit parseTimeUnit(final String value) {
        return null;
    }

    private static BenchmarkType parseType(final String value) {
        return BenchmarkType.THROUGHPUT;
    }
}
