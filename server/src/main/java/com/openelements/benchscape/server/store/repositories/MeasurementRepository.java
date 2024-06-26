package com.openelements.benchscape.server.store.repositories;

import com.openelements.benchscape.server.store.entities.MeasurementEntity;
import com.openelements.server.base.tenantdata.EntityWithTenantRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends EntityWithTenantRepository<MeasurementEntity>, JpaSpecificationExecutor<MeasurementEntity> {


    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 ORDER BY m.timestamp ASC")
    List<MeasurementEntity> findByBenchmarkId(final UUID benchmarkId);

    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 AND m.timestamp >= ?2 AND m.timestamp <= ?3 ORDER BY m.timestamp ASC")
    List<MeasurementEntity> find(final UUID benchmarkId, final Instant start, final Instant end);

    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 AND m.timestamp > ?2 ORDER BY m.timestamp DESC limit 1")
    Optional<MeasurementEntity> findFirstAfter(final UUID benchmarkId, final Instant end);

    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 AND m.timestamp < ?2 ORDER BY m.timestamp DESC limit 1")
    Optional<MeasurementEntity> findLastBefore(final UUID benchmarkId, final Instant start);

    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 ORDER BY m.timestamp DESC limit 100")
    List<MeasurementEntity> findNewest100(final UUID benchmarkId);

    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 ORDER BY m.timestamp ASC limit 1")
    Optional<MeasurementEntity> findFirst(final UUID benchmarkId);

    @NonNull
    @Query("SELECT m FROM Measurement m WHERE m.benchmarkId = ?1 ORDER BY m.timestamp DESC limit 1")
    Optional<MeasurementEntity> findLast(final UUID benchmarkId);

    @NonNull
    @Query("SELECT DISTINCT m.metadata.gitBranch FROM Measurement m WHERE m.benchmarkId = ?1")
    List<String> findDistinctBranchesByBenchmarkId(UUID benchmarkId);
}
