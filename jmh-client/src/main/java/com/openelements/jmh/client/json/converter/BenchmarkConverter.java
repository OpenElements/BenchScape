package com.openelements.jmh.client.json.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.openelements.benchscape.common.BenchmarkConfiguration;
import com.openelements.benchscape.common.BenchmarkExecution;
import com.openelements.benchscape.common.BenchmarkExecutionMetadata;
import com.openelements.benchscape.common.BenchmarkExecutionResult;
import com.openelements.benchscape.common.BenchmarkInfrastructure;
import com.openelements.benchscape.common.BenchmarkType;
import java.lang.reflect.Type;
import java.util.Objects;

/**
 * GSON (see {@link com.google.gson.Gson}) Converter for {@link BenchmarkExecution} instances.
 *
 * @see com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)
 */
public final class BenchmarkConverter implements JsonSerializer<BenchmarkExecution>,
        JsonDeserializer<BenchmarkExecution> {
    @Override
    public BenchmarkExecution deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        Objects.requireNonNull(json, "json must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final String benchmarkName = json.getAsJsonObject().get("benchmarkName").getAsString();
        final BenchmarkType type = context.deserialize(json.getAsJsonObject().get("type"), BenchmarkType.class);
        final BenchmarkInfrastructure infrastructure = context.deserialize(json.getAsJsonObject().get("infrastructure"),
                BenchmarkInfrastructure.class);
        final BenchmarkConfiguration configuration = context.deserialize(json.getAsJsonObject().get("configuration"),
                BenchmarkConfiguration.class);
        final BenchmarkExecutionMetadata execution = context.deserialize(json.getAsJsonObject().get("execution"),
                BenchmarkExecutionMetadata.class);
        final BenchmarkExecutionResult result = context.deserialize(json.getAsJsonObject().get("result"),
                BenchmarkExecutionResult.class);
        return new BenchmarkExecution(benchmarkName, type, infrastructure, configuration, execution, result);
    }

    @Override
    public JsonElement serialize(BenchmarkExecution src, Type typeOfSrc, JsonSerializationContext context) {
        Objects.requireNonNull(src, "src must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final JsonObject json = new JsonObject();
        json.addProperty("benchmarkName", src.benchmarkName());
        json.add("type", context.serialize(src.type(), BenchmarkType.class));
        json.add("infrastructure", context.serialize(src.infrastructure(), BenchmarkInfrastructure.class));
        json.add("configuration", context.serialize(src.configuration(), BenchmarkConfiguration.class));
        json.add("execution", context.serialize(src.execution(), BenchmarkExecutionMetadata.class));
        json.add("result", context.serialize(src.result(), BenchmarkExecutionResult.class));
        return json;
    }
}
