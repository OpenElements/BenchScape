package com.openelements.jmh.store.db.repositories;

import com.openelements.jmh.store.db.entities.BenchmarkEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BenchmarkRepository extends JpaRepository<BenchmarkEntity, Long> {

  @Query("SELECT b FROM BenchmarkEntity b WHERE b.name = ?1")
  Optional<BenchmarkEntity> findByName(String name);
}
