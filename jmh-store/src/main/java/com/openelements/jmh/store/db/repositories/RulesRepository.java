package com.openelements.jmh.store.db.repositories;

import com.openelements.jmh.store.db.entities.RulesEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RulesRepository extends JpaRepository<RulesEntity, Long> {

  @Query("SELECT b FROM RulesEntity b WHERE b.benchmarkId = ?1")
  Optional<RulesEntity> findForBenchmark(Long benchmarkId);

}
