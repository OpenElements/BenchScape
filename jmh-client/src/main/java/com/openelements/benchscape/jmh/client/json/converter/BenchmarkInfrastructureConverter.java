package com.openelements.benchscape.jmh.client.json.converter;

import static com.openelements.benchscape.jmh.client.json.converter.BenchmarkExecutionConverter.STRING_MAP_TYPE;

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
        final Map<String, String> systemProperties = context.deserialize(json.getAsJsonObject().get("systemProperties"),
                STRING_MAP_TYPE);
        final Map<String, String> environmentProperties = context.deserialize(
                json.getAsJsonObject().get("environmentProperties"), STRING_MAP_TYPE);
        return new BenchmarkInfrastructure(arch, availableProcessors, memory, osName, osVersion, jvmVersion, jvmName,
                systemProperties, environmentProperties, jmhVersion);
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
        json.add("systemProperties", context.serialize(src.systemProperties(), STRING_MAP_TYPE));
        json.add("environmentProperties", context.serialize(src.environmentProperties(), STRING_MAP_TYPE));
        return json;
    }
}
