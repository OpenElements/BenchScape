package com.openelements.server.base.tenantdata;

import com.openelements.server.base.data.DataBase;
import com.openelements.server.base.data.DataService;
import com.openelements.server.base.tenant.TenantService;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public abstract class AbstractServiceWithTenant<ENTITY extends AbstractEntityWithTenant, DATA extends DataBase> implements
        DataService<DATA> {

    protected String getCurrentTenantId() {
        final String tenantId = getTenantService().getCurrentTenant();
        if (tenantId == null) {
            throw new IllegalStateException("No tenant set");
        }
        if (tenantId.isBlank()) {
            throw new IllegalArgumentException("tenantId must not be blank");
        }
        return tenantId;
    }

    protected abstract TenantService getTenantService();

    protected abstract EntityWithTenantRepository<ENTITY> getRepository();

    @NonNull
    @Override
    public Optional<DATA> get(@NonNull UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        return getRepository().findByIdAndTenantId(id, getCurrentTenantId())
                .map(entity -> mapToData(entity));
    }

    @NonNull
    @Override
    public List<DATA> getAll() {
        return getRepository().getAllByTenantId(getCurrentTenantId()).stream()
                .map(entity -> mapToData(entity))
                .toList();
    }

    @NonNull
    @Override
    public DATA find(@NonNull UUID id) {
        return get(id).orElseThrow(() -> new IllegalArgumentException("No entity found for ID: " + id));
    }

    @NonNull
    @Override
    public DATA save(@NonNull DATA data) {
        Objects.requireNonNull(data, "data must not be null");
        final ENTITY entity = mapToEntity(data);
        entity.setTenantId(getCurrentTenantId());
        return mapToData(getRepository().save(entity));
    }

    @Override
    public long getCount() {
        return getRepository().countByTenantId(getCurrentTenantId());
    }

    @Override
    public void delete(@NonNull UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        final ENTITY entity = getRepository().findByIdAndTenantId(id, getCurrentTenantId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "No entity found for ID: " + id + " and tenant: " + getCurrentTenantId()));
        getRepository().delete(entity);
    }

    @NonNull
    protected ENTITY mapToEntity(final @NonNull DATA data) {
        Objects.requireNonNull(data, "data must not be null");
        final UUID id = data.id();
        final ENTITY entity;
        if (id == null) {
            entity = createNewEntity();
        } else {
            entity = getRepository().findByIdAndTenantId(id, getCurrentTenantId())
                    .orElseThrow(() -> new IllegalArgumentException("Entity with id " + id + " not found"));
        }
        ENTITY updatedEntity = updateEntity(entity, data);
        updatedEntity.setTenantId(getCurrentTenantId());
        return updatedEntity;
    }

    protected abstract ENTITY createNewEntity();

    protected abstract DATA mapToData(ENTITY entity);

    @NonNull
    protected abstract ENTITY updateEntity(@NonNull ENTITY entity, @NonNull DATA data);

}
