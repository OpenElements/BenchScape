package com.openelements.jmh.client.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.openelements.jmh.client.json.converter.BenchmarkConfigurationConverter;
import com.openelements.jmh.client.json.converter.BenchmarkConverter;
import com.openelements.jmh.client.json.converter.BenchmarkExecutionConverter;
import com.openelements.jmh.client.json.converter.BenchmarkInfrastructureConverter;
import com.openelements.jmh.client.json.converter.BenchmarkMeasurementConfigurationConverter;
import com.openelements.jmh.client.json.converter.BenchmarkResultConverter;
import com.openelements.jmh.client.json.converter.InstantConverter;
import com.openelements.jmh.common.BenchmarkConfiguration;
import com.openelements.jmh.common.BenchmarkExecution;
import com.openelements.jmh.common.BenchmarkExecutionMetadata;
import com.openelements.jmh.common.BenchmarkExecutionResult;
import com.openelements.jmh.common.BenchmarkInfrastructure;
import com.openelements.jmh.common.BenchmarkMeasurementConfiguration;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Instant;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Factory for creating JSON representations of {@link BenchmarkExecution} instances.
 */
public class BenchmarkJsonFactory {

    /**
     * Private constructor to prevent instantiation.
     */
    private BenchmarkJsonFactory() {
    }

    /**
     * Provides a {@link Gson} instance for serializing and deserializing {@link BenchmarkExecution} instances.
     *
     * @return the {@link Gson} instance
     */
    @NonNull
    private static Gson createGson() {
        return new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(Instant.class, new InstantConverter())
                .registerTypeAdapter(BenchmarkConfiguration.class, new BenchmarkConfigurationConverter())
                .registerTypeAdapter(BenchmarkExecution.class, new BenchmarkConverter())
                .registerTypeAdapter(BenchmarkExecutionMetadata.class, new BenchmarkExecutionConverter())
                .registerTypeAdapter(BenchmarkInfrastructure.class, new BenchmarkInfrastructureConverter())
                .registerTypeAdapter(BenchmarkMeasurementConfiguration.class,
                        new BenchmarkMeasurementConfigurationConverter())
                .registerTypeAdapter(BenchmarkExecutionResult.class, new BenchmarkResultConverter())
                .serializeSpecialFloatingPointValues()
                .create();
    }

    /**
     * Creates a JSON representation of the given {@link BenchmarkExecution} instances.
     *
     * @param benchmarks the {@link BenchmarkExecution} instances
     * @return the JSON representation
     */
    @NonNull
    public static String toJson(@NonNull final Collection<BenchmarkExecution> benchmarks) {
        Objects.requireNonNull(benchmarks, "benchmarks must not be null");
        return createGson().toJson(benchmarks);
    }

    /**
     * Creates a JSON representation of the given {@link BenchmarkExecution} instances.
     *
     * @param benchmarks the {@link BenchmarkExecution} instances
     * @return the JSON representation
     */
    @NonNull
    public static JsonElement toJsonTree(@NonNull final Set<BenchmarkExecution> benchmarks) {
        Objects.requireNonNull(benchmarks, "benchmarks must not be null");
        return createGson().toJsonTree(benchmarks);
    }

    /**
     * Creates a JSON representation of the given {@link BenchmarkExecution} instance.
     *
     * @param benchmark the {@link BenchmarkExecution} instance
     * @return the JSON representation
     */
    @NonNull
    public static String toJson(@NonNull final BenchmarkExecution benchmark) {
        Objects.requireNonNull(benchmark, "benchmark must not be null");
        return createGson().toJson(benchmark);
    }

    /**
     * Creates a JSON representation of the given {@link BenchmarkExecution} instance.
     *
     * @param benchmark the {@link BenchmarkExecution} instance
     * @return the JSON representation
     */
    @NonNull
    public static JsonElement toJsonTree(@NonNull final BenchmarkExecution benchmark) {
        Objects.requireNonNull(benchmark, "benchmark must not be null");
        return createGson().toJsonTree(benchmark);
    }

    /**
     * Parses the given JSON representation into a {@link Set} of {@link BenchmarkExecution} instances.
     *
     * @param jsonElement the JSON representation
     * @return the {@link Set} of {@link BenchmarkExecution} instances
     */
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

    /**
     * Parses the given JSON representation into a {@link BenchmarkExecution} instance.
     *
     * @param jsonElement the JSON representation
     * @return the {@link BenchmarkExecution} instance
     */
    @NonNull
    public static BenchmarkExecution parseBenchmark(@NonNull final JsonElement jsonElement) {
        Objects.requireNonNull(jsonElement, "jsonElement must not be null");
        return createGson().fromJson(jsonElement, BenchmarkExecution.class);
    }

}
