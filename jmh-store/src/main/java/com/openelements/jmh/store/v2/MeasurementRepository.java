package com.openelements.jmh.store.v2;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MeasurementRepository extends JpaRepository<MeasurementEntity, UUID> {

    @Query("SELECT m FROM MeasurementEntity m WHERE m.benchmarkId = ?1 AND m.timestamp >= ?2 AND m.timestamp <= ?3 ORDER BY m.timestamp ASC")
    List<MeasurementEntity> find(final String benchmarkId, final Instant start, final Instant end);
}
