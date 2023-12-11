package com.openelements.server.base.apikey;

import com.openelements.server.base.data.DataBase;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record ApiKeyData(UUID id, String name, String user, String key, LocalDateTime validUntil) implements DataBase {

    public ApiKeyData {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(user, "user must not be null");
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(validUntil, "validUntil must not be null");
    }

}
