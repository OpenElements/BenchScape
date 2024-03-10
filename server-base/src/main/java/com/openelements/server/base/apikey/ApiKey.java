package com.openelements.server.base.apikey;

import com.openelements.server.base.data.DataBase;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

public record ApiKey(@Nullable UUID id, @NonNull String name, @NonNull String user, @NonNull String key, @NonNull LocalDateTime validUntil) implements DataBase {

    public ApiKey {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(user, "user must not be null");
        Objects.requireNonNull(key, "key must not be null");
        Objects.requireNonNull(validUntil, "validUntil must not be null");
    }

}
