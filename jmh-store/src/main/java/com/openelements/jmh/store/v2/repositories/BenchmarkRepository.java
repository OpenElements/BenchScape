package com.openelements.jmh.store.v2.repositories;

import com.openelements.jmh.store.v2.entities.BenchmarkEntity;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BenchmarkRepository extends JpaRepository<BenchmarkEntity, UUID> {

    @NonNull
    List<BenchmarkEntity> findByName(String s);
}
