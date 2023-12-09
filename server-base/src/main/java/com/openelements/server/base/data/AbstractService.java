package com.openelements.server.base.data;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractService<ENTITY extends EntityBase, DATA extends DataBase> {

    @NonNull
    protected abstract EntityRepository<ENTITY> getRepository();

    @NonNull
    protected abstract DATA mapToData(@NonNull final ENTITY entity);

    @NonNull
    protected ENTITY mapToEntity(final @NonNull DATA data) {
        Objects.requireNonNull(data, "data must not be null");
        final UUID id = data.id();
        final ENTITY entity;
        if (id == null) {
            entity = createNewEntity();
        } else {
            entity = getRepository().findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Entity with id " + id + " not found"));
        }
        return updateEntity(entity, data);
    }

    @NonNull
    protected abstract ENTITY updateEntity(@NonNull ENTITY entity, @NonNull DATA data);

    @NonNull
    protected abstract ENTITY createNewEntity();

    @NonNull
    public Optional<DATA> get(@NonNull final UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        return getRepository().findById(id)
                .map(e -> mapToData(e));
    }

    @NonNull
    public Optional<DATA> get(@NonNull final String id) {
        Objects.requireNonNull(id, "id must not be null");
        return get(UUID.fromString(id));
    }

    @NonNull
    public List<DATA> getAll() {
        return getRepository().findAll().stream()
                .map(e -> mapToData(e))
                .toList();
    }

    @NonNull
    public DATA find(@NonNull final UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        return getRepository().findById(id)
                .map(e -> mapToData(e))
                .orElseThrow(() -> new IllegalArgumentException("No environment found for ID: " + id));
    }

    @NonNull
    public DATA find(@NonNull String id) {
        Objects.requireNonNull(id, "id must not be null");
        return find(UUID.fromString(id));
    }

    @NonNull
    public DATA save(final @NonNull DATA data) {
        Objects.requireNonNull(data, "dao must not be null");
        return mapToData(getRepository().save(mapToEntity(data)));
    }

    public void delete(@NonNull DATA data) {
        Objects.requireNonNull(data, "data must not be null");
        delete(data.id());
    }

    public void delete(@NonNull String id) {
        Objects.requireNonNull(id, "id must not be null");
        delete(UUID.fromString(id));
    }

    public void delete(@NonNull UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        getRepository().deleteById(id);
    }

    public long getCount() {
        return getRepository().count();
    }
}
