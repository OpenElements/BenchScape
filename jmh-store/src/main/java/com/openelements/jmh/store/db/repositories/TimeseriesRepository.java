package com.openelements.jmh.store.db.repositories;

import com.openelements.jmh.store.db.entities.TimeseriesEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TimeseriesRepository extends JpaRepository<TimeseriesEntity, Long> {

  @Query("SELECT b FROM TimeseriesEntity b WHERE b.benchmarkId = ?1")
  List<TimeseriesEntity> findAllForBenchmark(Long benchmarkId);
}
