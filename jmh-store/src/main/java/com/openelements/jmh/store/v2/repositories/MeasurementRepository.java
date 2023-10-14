package com.openelements.jmh.store.v2.repositories;

import com.openelements.jmh.store.v2.entities.MeasurementEntity;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends JpaRepository<MeasurementEntity, UUID> {

    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 AND m.timestamp >= ?2 AND m.timestamp <= ?3 ORDER BY m.timestamp ASC")
    List<MeasurementEntity> find(final String benchmarkId, final Instant start, final Instant end);

    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 AND m.timestamp > ?2 ORDER BY m.timestamp DESC limit 1")
    Optional<MeasurementEntity> findFirstAfter(final String benchmarkId, final Instant end);

    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 AND m.timestamp < ?2 ORDER BY m.timestamp DESC limit 1")
    Optional<MeasurementEntity> findLastBefore(final String benchmarkId, final Instant start);

    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 ORDER BY m.timestamp DESC limit 100")
    List<MeasurementEntity> findNewest(final String benchmarkId);
}
