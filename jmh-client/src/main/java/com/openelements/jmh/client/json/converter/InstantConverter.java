package com.openelements.jmh.client.json.converter;

import com.google.gson.*;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public final class InstantConverter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_INSTANT;

    public InstantConverter() {
    }

    public JsonElement serialize(@NonNull final Instant src, @NonNull final Type typeOfSrc, @NonNull final JsonSerializationContext context) {
        Objects.requireNonNull(src, "src must not be null");
        return new JsonPrimitive(FORMATTER.format(src));
    }

    public Instant deserialize(@NonNull final JsonElement json, @NonNull final Type typeOfT, @NonNull final JsonDeserializationContext context) throws JsonParseException {
        Objects.requireNonNull(json, "json must not be null");
        return (Instant) FORMATTER.parse(json.getAsString(), Instant::from);
    }
}


