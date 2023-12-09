package com.openelements.benchscape.server.store.repositories;

import com.openelements.benchscape.server.store.entities.BenchmarkEntity;
import com.openelements.server.base.data.EntityRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface BenchmarkRepository extends EntityRepository<BenchmarkEntity> {

    @NonNull
    Optional<BenchmarkEntity> findByName(String s);
}
