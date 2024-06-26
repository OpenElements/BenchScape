package com.openelements.benchscape.jmh.client.json.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.openelements.benchscape.jmh.model.BenchmarkExecutionMetadata;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Objects;

/**
 * GSON (see {@link Gson}) Converter for {@link BenchmarkExecutionMetadata} instances.
 *
 * @see com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)
 */
public final class BenchmarkExecutionMetadataConverter implements JsonSerializer<BenchmarkExecutionMetadata>,
        JsonDeserializer<BenchmarkExecutionMetadata> {

    @Override
    public BenchmarkExecutionMetadata deserialize(@NonNull final JsonElement json, @NonNull final Type typeOfT,
            @NonNull final JsonDeserializationContext context)
            throws JsonParseException {
        Objects.requireNonNull(json, "json must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final Instant startTime = context.deserialize(json.getAsJsonObject().get("startTime"), Instant.class);
        final Instant warmupTime = context.deserialize(json.getAsJsonObject().get("warmupTime"), Instant.class);
        final Instant measurementTime = context.deserialize(json.getAsJsonObject().get("measurementTime"),
                Instant.class);
        final Instant stopTime = context.deserialize(json.getAsJsonObject().get("stopTime"), Instant.class);
        final long warmupOps = context.deserialize(json.getAsJsonObject().get("warmupOps"), long.class);
        final long measurementOps = context.deserialize(json.getAsJsonObject().get("measurementOps"), long.class);
        return new BenchmarkExecutionMetadata(startTime, warmupTime, measurementTime, stopTime, warmupOps,
                measurementOps);
    }

    @Override
    public JsonElement serialize(@NonNull final BenchmarkExecutionMetadata src, @NonNull final Type typeOfSrc,
            @NonNull final JsonSerializationContext context) {
        Objects.requireNonNull(src, "src must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final JsonObject json = new JsonObject();
        json.add("startTime", context.serialize(src.startTime(), Instant.class));
        json.add("warmupTime", context.serialize(src.warmupTime(), Instant.class));
        json.add("measurementTime", context.serialize(src.measurementTime(), Instant.class));
        json.add("stopTime", context.serialize(src.stopTime(), Instant.class));
        json.addProperty("warmupOps", src.warmupOps());
        json.addProperty("measurementOps", src.measurementOps());
        return json;
    }
}
