package com.openelements.server.base.apikey;

import com.openelements.server.base.tenantdata.EntityWithTenantRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyEntityRepository extends EntityWithTenantRepository<ApiKeyEntity> {

}
