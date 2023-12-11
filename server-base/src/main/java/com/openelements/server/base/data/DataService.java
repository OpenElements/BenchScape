package com.openelements.server.base.data;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public interface DataService<DATA extends DataBase> {

    @NonNull
    Optional<DATA> get(@NonNull UUID id);

    @NonNull
    default Optional<DATA> get(@NonNull final String id) {
        Objects.requireNonNull(id, "id must not be null");
        return get(UUID.fromString(id));
    }

    @NonNull
    List<DATA> getAll();

    @NonNull
    default DATA find(@NonNull UUID id) {
        return get(id).orElseThrow(() -> new IllegalArgumentException("No data found for ID: " + id));
    }

    @NonNull
    default DATA find(@NonNull String id) {
        Objects.requireNonNull(id, "id must not be null");
        return find(UUID.fromString(id));
    }

    @NonNull
    DATA save(@NonNull DATA data);

    void delete(@NonNull UUID id);

    default void delete(@NonNull DATA data) {
        Objects.requireNonNull(data, "data must not be null");
        delete(data.id());
    }

    default void delete(@NonNull String id) {
        Objects.requireNonNull(id, "id must not be null");
        delete(UUID.fromString(id));
    }

    long getCount();
}
