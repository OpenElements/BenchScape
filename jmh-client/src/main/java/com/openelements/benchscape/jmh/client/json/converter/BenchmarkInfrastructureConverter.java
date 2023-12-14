package com.openelements.benchscape.jmh.client.json.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.openelements.benchscape.jmh.model.BenchmarkInfrastructure;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * GSON (see {@link Gson}) Converter for {@link BenchmarkInfrastructure} instances.
 *
 * @see com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)
 */
public final class BenchmarkInfrastructureConverter implements JsonSerializer<BenchmarkInfrastructure>,
        JsonDeserializer<BenchmarkInfrastructure> {

    @Override
    public BenchmarkInfrastructure deserialize(@NonNull final JsonElement json, @NonNull final Type typeOfT,
            @NonNull final JsonDeserializationContext context)
            throws JsonParseException {
        Objects.requireNonNull(json, "json must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final String arch = json.getAsJsonObject().get("arch").getAsString();
        final int availableProcessors = json.getAsJsonObject().get("availableProcessors").getAsInt();
        final long memory = json.getAsJsonObject().get("memory").getAsLong();
        final String osName = json.getAsJsonObject().get("osName").getAsString();
        final String osVersion = json.getAsJsonObject().get("osVersion").getAsString();
        final String jvmVersion = json.getAsJsonObject().get("jvmVersion").getAsString();
        final String jvmName = json.getAsJsonObject().get("jvmName").getAsString();
        final String jmhVersion = json.getAsJsonObject().get("jmhVersion").getAsString();
        Map<String, String> systemProperties = convertProperties(
                json.getAsJsonObject().get("systemProperties").getAsJsonArray());
        Map<String, String> envProperties = convertProperties(
                json.getAsJsonObject().get("envProperties").getAsJsonArray());
        return new BenchmarkInfrastructure(arch, availableProcessors, memory, osName, osVersion, jvmVersion, jvmName,
                systemProperties, envProperties, jmhVersion);
    }

    @Override
    public JsonElement serialize(@NonNull final BenchmarkInfrastructure src, @NonNull final Type typeOfSrc,
            @NonNull final JsonSerializationContext context) {
        Objects.requireNonNull(src, "src must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final JsonObject json = new JsonObject();
        json.addProperty("arch", src.arch());
        json.addProperty("availableProcessors", src.availableProcessors());
        json.addProperty("memory", src.memory());
        json.addProperty("osName", src.osName());
        json.addProperty("osVersion", src.osVersion());
        json.addProperty("jvmVersion", src.jvmVersion());
        json.addProperty("jvmName", src.jvmName());
        json.addProperty("jmhVersion", src.jmhVersion());
        json.addProperty("systemProperties", convertProperties(src.systemProperties()).toString());
        json.addProperty("envProperties", convertProperties(src.environmentProperties()).toString());
        return json;
    }

    private static Map<String, String> convertProperties(final JsonArray array) {
        Objects.requireNonNull(array, "array must not be null");
        Map<String, String> result = new HashMap<>();
        array.forEach(e -> {
            final JsonObject obj = e.getAsJsonObject();
            final String key = obj.get("key").getAsString();
            final String value = obj.get("value").getAsString();
            result.put(key, value);
        });
        return Collections.unmodifiableMap(result);
    }

    private static JsonArray convertProperties(final Map<String, String> map) {
        Objects.requireNonNull(map, "map must not be null");
        JsonArray result = new JsonArray();
        map.forEach((k, v) -> {
            final JsonObject obj = new JsonObject();
            obj.addProperty("key", k);
            obj.addProperty("value", v);
            result.add(obj);
        });
        return result;
    }
}
