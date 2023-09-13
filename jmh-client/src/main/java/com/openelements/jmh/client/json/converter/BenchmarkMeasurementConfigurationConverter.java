package com.openelements.jmh.client.json.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.openelements.benchscape.common.BenchmarkMeasurementConfiguration;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * GSON (see {@link Gson}) Converter for {@link BenchmarkMeasurementConfiguration} instances.
 *
 * @see com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)
 */
public final class BenchmarkMeasurementConfigurationConverter implements
        JsonSerializer<BenchmarkMeasurementConfiguration>,
        JsonDeserializer<BenchmarkMeasurementConfiguration> {
    @Override
    public BenchmarkMeasurementConfiguration deserialize(@NonNull final JsonElement json, @NonNull final Type typeOfT,
            @NonNull final JsonDeserializationContext context) throws JsonParseException {
        Objects.requireNonNull(json, "json must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final int iterations = json.getAsJsonObject().get("iterations").getAsInt();
        final long time = json.getAsJsonObject().get("time").getAsLong();
        final int batchSize = json.getAsJsonObject().get("batchSize").getAsInt();
        final TimeUnit timeUnit = context.deserialize(json.getAsJsonObject().get("timeUnit"), TimeUnit.class);
        return new BenchmarkMeasurementConfiguration(iterations, time, timeUnit, batchSize);
    }

    @Override
    public JsonElement serialize(@NonNull final BenchmarkMeasurementConfiguration src, @NonNull final Type typeOfSrc,
            @NonNull final JsonSerializationContext context) {
        Objects.requireNonNull(src, "src must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final JsonObject json = new JsonObject();
        json.addProperty("iterations", src.iterations());
        json.addProperty("time", src.time());
        json.add("timeUnit", context.serialize(src.timeUnit(), TimeUnit.class));
        json.addProperty("batchSize", src.batchSize());
        return json;
    }
}
