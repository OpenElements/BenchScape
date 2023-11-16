package com.openelements.benchscape.server.store.services;

import com.openelements.benchscape.server.store.entities.EntityBase;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class AbstractService<ENTITY extends EntityBase, DAO> {

    @NonNull
    protected abstract JpaRepository<ENTITY, UUID> getRepository();

    @NonNull
    protected abstract DAO map(@NonNull final ENTITY entity);

    @NonNull
    protected abstract ENTITY mapToEntity(final @NonNull DAO dao);

    @NonNull
    public Optional<DAO> get(@NonNull final UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        return getRepository().findById(id)
                .map(e -> map(e));
    }

    @NonNull
    public Optional<DAO> get(@NonNull final String id) {
        Objects.requireNonNull(id, "id must not be null");
        return get(UUID.fromString(id));
    }

    @NonNull
    public List<DAO> getAll() {
        return getRepository().findAll().stream()
                .map(e -> map(e))
                .toList();
    }

    @NonNull
    public DAO find(@NonNull final UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        return getRepository().findById(id)
                .map(e -> map(e))
                .orElseThrow(() -> new IllegalArgumentException("No environment found for ID: " + id));
    }

    @NonNull
    public DAO find(@NonNull String id) {
        Objects.requireNonNull(id, "id must not be null");
        return find(UUID.fromString(id));
    }

    @NonNull
    public DAO save(final @NonNull DAO dao) {
        return map(getRepository().save(mapToEntity(dao)));
    }

    public void delete(@NonNull String id) {
        Objects.requireNonNull(id, "id must not be null");
        delete(UUID.fromString(id));
    }

    public void delete(@NonNull UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        getRepository().deleteById(id);
    }
}
