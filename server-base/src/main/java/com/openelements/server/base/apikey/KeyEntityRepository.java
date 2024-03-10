package com.openelements.server.base.apikey;

import com.openelements.server.base.tenantdata.EntityWithTenantRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface KeyEntityRepository extends EntityWithTenantRepository<ApiKeyEntity> {

    @NonNull
    @Query("SELECT k FROM ApiKeyEntity k WHERE k.user = ?1 AND k.keyHash = ?2")
    Optional<ApiKeyEntity> findByUserAndHash(String user, String hash);
}
