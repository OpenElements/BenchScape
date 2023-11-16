package com.openelements.jmh.store.store.repositories;

import com.openelements.jmh.store.store.entities.BenchmarkEntity;
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
