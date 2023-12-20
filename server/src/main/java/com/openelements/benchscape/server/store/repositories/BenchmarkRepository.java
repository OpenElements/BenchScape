package com.openelements.benchscape.server.store.repositories;

import com.openelements.benchscape.server.store.entities.BenchmarkEntity;
import com.openelements.server.base.tenantdata.EntityWithTenantRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface BenchmarkRepository extends EntityWithTenantRepository<BenchmarkEntity> {

    @NonNull
    List<BenchmarkEntity> findByNameAndTenantId(String name, String currentTenantId);
}
