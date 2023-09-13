package com.openelements.jmh.client.json.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.openelements.benchscape.common.BenchmarkExecutionResult;
import com.openelements.benchscape.common.BenchmarkUnit;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * GSON (see {@link Gson}) Converter for {@link BenchmarkExecutionResult} instances.
 *
 * @see com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)
 */
public final class BenchmarkResultConverter implements JsonSerializer<BenchmarkExecutionResult>,
        JsonDeserializer<BenchmarkExecutionResult> {

    @Override
    public BenchmarkExecutionResult deserialize(@NonNull final JsonElement json, @NonNull final Type typeOfT,
            @NonNull final JsonDeserializationContext context)
            throws JsonParseException {
        Objects.requireNonNull(json, "json must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final BenchmarkUnit unit = context.deserialize(json.getAsJsonObject().get("unit"), BenchmarkUnit.class);
        final double value = json.getAsJsonObject().get("value").getAsDouble();
        final Double error = getValueOrNull(json, "error");
        final Double min = getValueOrNull(json, "min");
        final Double max = getValueOrNull(json, "max");
        return new BenchmarkExecutionResult(value, error, min, max, unit);
    }

    @Override
    public JsonElement serialize(@NonNull final BenchmarkExecutionResult src, @NonNull final Type typeOfSrc,
            @NonNull final JsonSerializationContext context) {
        Objects.requireNonNull(src, "src must not be null");
        Objects.requireNonNull(context, "context must not be null");

        final JsonObject json = new JsonObject();
        json.add("unit", context.serialize(src.unit()));
        json.addProperty("value", src.value());
        if (src.error() != null) {
            json.addProperty("error", src.error());
        }
        if (src.min() != null) {
            json.addProperty("min", src.min());
        }
        if (src.max() != null) {
            json.addProperty("max", src.max());
        }
        return json;
    }

    private static Double getValueOrNull(@NonNull final JsonElement json, @NonNull final String valueName) {
        Objects.requireNonNull(json, "json must not be null");
        Objects.requireNonNull(valueName, "valueName must not be null");
        if (json.getAsJsonObject().has(valueName)) {
            return null;
        } else {
            return json.getAsJsonObject().get(valueName).getAsDouble();
        }
    }
}
