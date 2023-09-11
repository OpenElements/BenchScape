package com.openelements.jmh.client.json.converter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.openelements.jmh.common.BenchmarkInfrastructure;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.lang.reflect.Type;
import java.util.Objects;

public final class BenchmarkInfrastructureConverter implements JsonSerializer<BenchmarkInfrastructure>,
        JsonDeserializer<BenchmarkInfrastructure> {

    @Override
    public BenchmarkInfrastructure deserialize(@NonNull final JsonElement json, @NonNull final Type typeOfT, @NonNull final JsonDeserializationContext context)
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
        final String jmhVendor = json.getAsJsonObject().get("jmhVendor").getAsString();
        return new BenchmarkInfrastructure(arch, availableProcessors, memory, osName, osVersion, jvmVersion, jvmName,
                jmhVendor);
    }

    @Override
    public JsonElement serialize(@NonNull final BenchmarkInfrastructure src, @NonNull final Type typeOfSrc, @NonNull final JsonSerializationContext context) {
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
        json.addProperty("jmhVendor", src.jmhVersion());
        return json;
    }
}
