package com.openelements.server.base.tenantdata;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface EntityWithTenantRepository<ENTITY extends AbstractEntityWithTenant> extends Repository<ENTITY, UUID> {


    Optional<ENTITY> findByIdAndTenantId(UUID id, String currentTenantId);

    Collection<ENTITY> getAllByTenantId(String currentTenantId);

    ENTITY save(ENTITY entity);

    void delete(ENTITY entity);

    long countByTenantId(String currentTenantId);
}
