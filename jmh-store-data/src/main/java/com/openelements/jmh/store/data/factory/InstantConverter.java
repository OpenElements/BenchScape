package com.openelements.jmh.store.data.factory;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantConverter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {
    private static final DateTimeFormatter FORMATTER;

    public InstantConverter() {
    }

    public JsonElement serialize(Instant src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(FORMATTER.format(src));
    }

    public Instant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        return (Instant) FORMATTER.parse(json.getAsString(), Instant::from);
    }

    static {
        FORMATTER = DateTimeFormatter.ISO_INSTANT;
    }
}


