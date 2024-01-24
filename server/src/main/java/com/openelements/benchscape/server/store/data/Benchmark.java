package com.openelements.benchscape.server.store.data;

import com.openelements.server.base.data.DataBase;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public record Benchmark(@Nullable UUID id, @NonNull String name, @NonNull Map<String, String> params,
                        @NonNull List<String> tags) implements
        DataBase {
    public Benchmark {
        Objects.requireNonNull(name, "name must not be null");
        Objects.requireNonNull(params, "params must not be null");
        Objects.requireNonNull(tags, "tags must not be null");
    }
}
