package com.openelements.benchscape.jmh.client.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.openelements.benchscape.jmh.client.json.converter.BenchmarkConfigurationConverter;
import com.openelements.benchscape.jmh.client.json.converter.BenchmarkConverter;
import com.openelements.benchscape.jmh.client.json.converter.BenchmarkExecutionConverter;
import com.openelements.benchscape.jmh.client.json.converter.BenchmarkInfrastructureConverter;
import com.openelements.benchscape.jmh.client.json.converter.BenchmarkMeasurementConfigurationConverter;
import com.openelements.benchscape.jmh.client.json.converter.BenchmarkResultConverter;
import com.openelements.benchscape.jmh.client.json.converter.InstantConverter;
import com.openelements.benchscape.jmh.model.BenchmarkConfiguration;
import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import com.openelements.benchscape.jmh.model.BenchmarkExecutionMetadata;
import com.openelements.benchscape.jmh.model.BenchmarkExecutionResult;
import com.openelements.benchscape.jmh.model.BenchmarkInfrastructure;
import com.openelements.benchscape.jmh.model.BenchmarkMeasurementConfiguration;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;

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
}
