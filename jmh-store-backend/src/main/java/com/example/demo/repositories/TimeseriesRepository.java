package com.example.demo.repositories;

import com.example.demo.data.TimeseriesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeseriesRepository extends JpaRepository<TimeseriesEntity, Long> {

    @Query("SELECT b FROM TimeseriesEntity b WHERE b.benchmarkId = ?1")
    List<TimeseriesEntity> findAllForBenchmark(String benchmarkId);
}
