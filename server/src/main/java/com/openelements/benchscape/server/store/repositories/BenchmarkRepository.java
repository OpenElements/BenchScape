package com.openelements.benchscape.server.store.repositories;

import com.openelements.benchscape.server.store.entities.BenchmarkEntity;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BenchmarkRepository extends JpaRepository<BenchmarkEntity, UUID> {

    @NonNull
    Optional<BenchmarkEntity> findByName(String s);
}
