package com.openelements.benchscape.jmh.client.json.converter;

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
 * GSON (see {@link com.google.gson.Gson}) Converter for {@link BenchmarkInfrastructure} instances.
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
                json.getAsJsonObject().get("systemProperties").getAsJsonObject());
        Map<String, String> envProperties = convertProperties(
                json.getAsJsonObject().get("environmentProperties").getAsJsonObject());
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
        json.add("systemProperties", convertProperties(src.systemProperties()));
        json.add("environmentProperties", convertProperties(src.environmentProperties()));
        return json;
    }

    private static Map<String, String> convertProperties(final JsonObject jsonMap) {
        Objects.requireNonNull(jsonMap, "jsonMap must not be null");
        Map<String, String> result = new HashMap<>();
        jsonMap.entrySet().forEach(e -> {
            result.put(e.getKey(), e.getValue().getAsString());
        });
        return Collections.unmodifiableMap(result);
    }

    private static JsonObject convertProperties(final Map<String, String> map) {
        Objects.requireNonNull(map, "map must not be null");
        JsonObject result = new JsonObject();
        map.forEach((k, v) -> result.addProperty(k, v));
        return result;
    }
}
