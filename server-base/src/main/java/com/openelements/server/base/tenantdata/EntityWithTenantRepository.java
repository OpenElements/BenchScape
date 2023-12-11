package com.openelements.server.base.tenantdata;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

@NoRepositoryBean
public interface EntityWithTenantRepository<ENTITY extends AbstractEntityWithTenant> extends Repository<ENTITY, UUID> {


    Optional<ENTITY> findByIdAndTenantId(UUID id, String currentTenantId);

    List<ENTITY> findAllByTenantId(String currentTenantId);

    ENTITY save(ENTITY entity);

    void delete(ENTITY entity);

    long countByTenantId(String currentTenantId);
}
