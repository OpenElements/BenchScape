package com.openelements.benchscape.jmh.client.json.converter;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.openelements.benchscape.jmh.model.BenchmarkGitState;
import com.openelements.benchscape.jmh.model.BenchmarkInfrastructure;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.lang.reflect.Type;
import java.util.Objects;
import java.util.Set;

/**
 * GSON (see {@link com.google.gson.Gson}) Converter for {@link BenchmarkInfrastructure} instances.
 *
 * @see com.google.gson.GsonBuilder#registerTypeAdapter(Type, Object)
 */
public final class BenchmarkGitStateConverter implements JsonSerializer<BenchmarkGitState>,
        JsonDeserializer<BenchmarkGitState> {

    @Override
    public BenchmarkGitState deserialize(@NonNull final JsonElement json, @NonNull final Type typeOfT,
            @NonNull final JsonDeserializationContext context)
            throws JsonParseException {
        Objects.requireNonNull(json, "json must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final String originUrl = json.getAsJsonObject().get("originUrl").getAsString();
        final String branch = json.getAsJsonObject().get("branch").getAsString();
        final String commitId = json.getAsJsonObject().get("commitId").getAsString();
        final Set<String> tags = json.getAsJsonObject().get("tags").getAsJsonArray().asList().stream()
                .map(JsonElement::getAsString).collect(java.util.stream.Collectors.toSet());
        final boolean dirty = json.getAsJsonObject().get("dirty").getAsBoolean();
        return new BenchmarkGitState(originUrl, branch, commitId, tags, dirty);
    }

    @Override
    public JsonElement serialize(@NonNull final BenchmarkGitState src, @NonNull final Type typeOfSrc,
            @NonNull final JsonSerializationContext context) {
        Objects.requireNonNull(src, "src must not be null");
        Objects.requireNonNull(context, "context must not be null");
        final JsonObject json = new JsonObject();
        json.addProperty("originUrl", src.originUrl());
        json.addProperty("branch", src.branch());
        json.addProperty("commitId", src.commitId());
        JsonArray tags = new JsonArray();
        src.tags().forEach(tags::add);
        json.add("tags", tags);
        json.addProperty("dirty", src.dirty());
        return json;
    }
}
