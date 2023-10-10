package com.openelements.jmh.store.v2;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, UUID> {

    @Query("SELECT m FROM MeasurementEntity m WHERE m.benchmarkId = ?1 AND m.timestamp >= ?2 AND m.timestamp <= ?3 ORDER BY m.timestamp ASC")
    List<MeasurementEntity> find(final String benchmarkId, final Instant start, final Instant end);

    @Query("SELECT m FROM MeasurementEntity m WHERE m.benchmarkId = ?1 AND m.timestamp > ?2 ORDER BY m.timestamp DESC limit 1")
    Optional<MeasurementEntity> findFirstAfter(final String benchmarkId, final Instant end);

    @Query("SELECT m FROM MeasurementEntity m WHERE m.benchmarkId = ?1 AND m.timestamp < ?2 ORDER BY m.timestamp DESC limit 1")
    Optional<MeasurementEntity> findLastBefore(final String benchmarkId, final Instant start);

    @Query("SELECT m FROM MeasurementEntity m WHERE m.benchmarkId = ?1 ORDER BY m.timestamp DESC limit 100")
    List<MeasurementEntity> findNewest(final String benchmarkId);
}
