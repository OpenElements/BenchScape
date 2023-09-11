package com.openelements.jmh.client.json;

import com.google.gson.*;
import com.openelements.jmh.common.BenchmarkExecution;
import com.openelements.jmh.common.BenchmarkConfiguration;
import com.openelements.jmh.common.BenchmarkExecutionMetadata;
import com.openelements.jmh.common.BenchmarkInfrastructure;
import com.openelements.jmh.common.BenchmarkMeasurementConfiguration;

import com.openelements.jmh.common.BenchmarkExecutionResult;
import com.openelements.jmh.client.json.converter.BenchmarkConfigurationConverter;
import com.openelements.jmh.client.json.converter.BenchmarkConverter;
import com.openelements.jmh.client.json.converter.BenchmarkExecutionConverter;
import com.openelements.jmh.client.json.converter.BenchmarkInfrastructureConverter;
import com.openelements.jmh.client.json.converter.BenchmarkMeasurementConfigurationConverter;
import com.openelements.jmh.client.json.converter.BenchmarkResultConverter;
import com.openelements.jmh.client.json.converter.InstantConverter;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Instant;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BenchmarkJsonFactory {

    private BenchmarkJsonFactory() {
    }

    @NonNull
    private static Gson createGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantConverter())
                .registerTypeAdapter(BenchmarkConfiguration.class, new BenchmarkConfigurationConverter())
                .registerTypeAdapter(BenchmarkExecution.class, new BenchmarkConverter())
                .registerTypeAdapter(BenchmarkExecutionMetadata.class, new BenchmarkExecutionConverter())
                .registerTypeAdapter(BenchmarkInfrastructure.class, new BenchmarkInfrastructureConverter())
                .registerTypeAdapter(BenchmarkMeasurementConfiguration.class, new BenchmarkMeasurementConfigurationConverter())
                .registerTypeAdapter(BenchmarkExecutionResult.class, new BenchmarkResultConverter())
                .serializeSpecialFloatingPointValues()
                .create();
    }

    @NonNull
    public static String toJson(@NonNull final Set<BenchmarkExecution> benchmarks) {
        Objects.requireNonNull(benchmarks, "benchmarks must not be null");
        return createGson().toJson(benchmarks);
    }

    @NonNull
    public static JsonElement toJsonTree(@NonNull final Set<BenchmarkExecution> benchmarks) {
        Objects.requireNonNull(benchmarks, "benchmarks must not be null");
        return createGson().toJsonTree(benchmarks);
    }

    @NonNull
    public static String toJson(@NonNull final BenchmarkExecution benchmark) {
        Objects.requireNonNull(benchmark, "benchmark must not be null");
        return createGson().toJson(benchmark);
    }

    @NonNull
    public static JsonElement toJsonTree(@NonNull final BenchmarkExecution benchmark) {
        Objects.requireNonNull(benchmark, "benchmark must not be null");
        return createGson().toJsonTree(benchmark);
    }

    @NonNull
    public static Set<BenchmarkExecution> parse(@NonNull final JsonElement jsonElement) {
        Objects.requireNonNull(jsonElement, "jsonElement must not be null");
        if (!jsonElement.isJsonArray()) {
            throw new IllegalArgumentException("jsonElement must be a JsonArray");
        }
        final Set<BenchmarkExecution> result = new HashSet<>();
        jsonElement.getAsJsonArray()
                .forEach(child -> createGson().fromJson(child, BenchmarkExecution.class));
        return Collections.unmodifiableSet(result);
    }

    @NonNull
    public static BenchmarkExecution parseBenchmark(@NonNull final JsonElement jsonElement) {
        Objects.requireNonNull(jsonElement, "jsonElement must not be null");
        return createGson().fromJson(jsonElement, BenchmarkExecution.class);
    }

}
