package com.openelements.benchscape.server.store.repositories;

import com.openelements.benchscape.server.store.entities.EnvironmentEntity;
import com.openelements.server.base.data.EntityRepository;
import com.openelements.server.base.tenantdata.EntityWithTenantRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends EntityWithTenantRepository<EnvironmentEntity> {
}
