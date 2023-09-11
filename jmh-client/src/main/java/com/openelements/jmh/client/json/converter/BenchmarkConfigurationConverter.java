package com.openelements.jmh.client.json.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.openelements.jmh.common.BenchmarkConfiguration;
import com.openelements.jmh.common.BenchmarkMeasurementConfiguration;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public final class BenchmarkConfigurationConverter implements JsonSerializer<BenchmarkConfiguration>,
        JsonDeserializer<BenchmarkConfiguration> {
    @Override
    public BenchmarkConfiguration deserialize(@NonNull final JsonElement json, @NonNull final Type typeOfT, @NonNull final JsonDeserializationContext context)
            throws JsonParseException {
        Objects.requireNonNull(json, "json must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final int threads = json.getAsJsonObject().get("threads").getAsInt();
        final int forks = json.getAsJsonObject().get("forks").getAsInt();
        final long timeout = json.getAsJsonObject().get("timeout").getAsLong();
        final TimeUnit timeoutUnit = context.deserialize(json.getAsJsonObject().get("timeoutUnit"), TimeUnit.class);
        final BenchmarkMeasurementConfiguration measurementConfiguration = context.deserialize(json.getAsJsonObject().get("measurementConfiguration"), BenchmarkMeasurementConfiguration.class);
        final BenchmarkMeasurementConfiguration warmupConfiguration = context.deserialize(json.getAsJsonObject().get("warmupConfiguration"), BenchmarkMeasurementConfiguration.class);
        return new BenchmarkConfiguration(threads, forks, timeout, timeoutUnit, measurementConfiguration, warmupConfiguration);
    }

    @Override
    public JsonElement serialize(@NonNull final BenchmarkConfiguration src, @NonNull final Type typeOfSrc, @NonNull final JsonSerializationContext context) {
        Objects.requireNonNull(src, "src must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final JsonObject json = new JsonObject();
        json.addProperty("threads", src.threads());
        json.addProperty("forks", src.forks());
        json.addProperty("timeout", src.timeout());
        json.add("timeoutUnit", context.serialize(src.timeoutUnit(), TimeUnit.class));
        json.add("measurementConfiguration", context.serialize(src.measurementConfiguration(), BenchmarkMeasurementConfiguration.class));
        json.add("warmupConfiguration", context.serialize(src.warmupConfiguration(), BenchmarkMeasurementConfiguration.class));
        return json;
    }
}
