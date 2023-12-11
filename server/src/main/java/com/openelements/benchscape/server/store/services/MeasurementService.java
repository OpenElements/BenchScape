package com.openelements.benchscape.server.store.services;

import com.openelements.benchscape.server.store.data.DateTimePeriode;
import com.openelements.benchscape.server.store.data.Measurement;
import com.openelements.benchscape.server.store.data.MeasurementMetadata;
import com.openelements.benchscape.server.store.data.MeasurementQuery;
import com.openelements.benchscape.server.store.entities.MeasurementEntity;
import com.openelements.benchscape.server.store.entities.MeasurementMetadataEntity;
import com.openelements.benchscape.server.store.repositories.MeasurementRepository;
import com.openelements.server.base.data.AbstractDataService;
import com.openelements.server.base.data.EntityRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MeasurementService extends AbstractDataService<MeasurementEntity, Measurement> {

    private final MeasurementRepository measurementRepository;

    private final EnvironmentService environmentService;

    @Autowired
    public MeasurementService(@NonNull final MeasurementRepository measurementRepository,
            @NonNull final EnvironmentService environmentService) {
        this.measurementRepository = Objects.requireNonNull(measurementRepository,
                "measurementRepository must not be null");
        this.environmentService = Objects.requireNonNull(environmentService,
                "environmentService must not be null");
    }

    @NonNull
    public List<Measurement> find(final @NonNull MeasurementQuery query) {
        Objects.requireNonNull(query, "query must not be null");
        return measurementRepository.find(UUID.fromString(query.benchmarkId()), query.start(), query.end())
                .stream()
                .map(m -> mapToData(m))
                .filter(m -> isMatchingEnvironment(m, query.environmentIds()))
                .map(m -> m.withUnit(query.unit()))
                .toList();
    }

    @NonNull
    public Optional<Measurement> findPrevious(@NonNull final Measurement measurement) {
        Objects.requireNonNull(measurement, "measurement must not be null");
        final UUID id = Optional.ofNullable(measurement.id())
                .orElseThrow(() -> new IllegalArgumentException("measurement.id() must not be null"));
        return measurementRepository.findLastBefore(id, measurement.timestamp())
                .map(m -> mapToData(m));
    }

    @NonNull
    public Optional<Measurement> findNext(@NonNull final Measurement measurement) {
        Objects.requireNonNull(measurement, "measurement must not be null");
        final UUID id = Optional.ofNullable(measurement.id())
                .orElseThrow(() -> new IllegalArgumentException("measurement.id() must not be null"));
        return measurementRepository.findFirstAfter(id, measurement.timestamp())
                .map(m -> mapToData(m));
    }

    @NonNull
    @Override
    protected EntityRepository<MeasurementEntity> getRepository() {
        return measurementRepository;
    }

    @NonNull
    @Override
    protected Measurement mapToData(@NonNull MeasurementEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Measurement(entity.getId(), entity.getTimestamp(), entity.getValue(),
                entity.getError(), entity.getMin(), entity.getMax(), entity.getUnit());

    }

    @NonNull
    @Override
    protected MeasurementEntity updateEntity(@NonNull MeasurementEntity entity,
            @NonNull Measurement measurement) {
        Objects.requireNonNull(entity, "entity must not be null");
        Objects.requireNonNull(measurement, "measurement must not be null");
        entity.setId(measurement.id());
        entity.setTimestamp(measurement.timestamp());
        entity.setValue(measurement.value());
        entity.setError(measurement.error());
        entity.setMin(measurement.min());
        entity.setMax(measurement.max());
        entity.setUnit(measurement.unit());
        return entity;
    }

    @NonNull
    @Override
    protected MeasurementEntity createNewEntity() {
        return new MeasurementEntity();
    }

    private boolean isMatchingEnvironment(@NonNull final Measurement measurement,
            @NonNull Collection<String> environmentIds) {
        Objects.requireNonNull(measurement, "measurement must not be null");
        Objects.requireNonNull(environmentIds, "environmentIds must not be null");
        if (environmentIds.isEmpty()) {
            return true;
        }
        final MeasurementMetadata metadata = getMetadataForMeasurement(measurement.id());
        return environmentIds.stream()
                .map(id -> environmentService.isMatchingEnvironment(metadata, id))
                .filter(result -> !result)
                .findAny()
                .isPresent();
    }

    public MeasurementMetadata getMetadataForMeasurement(String measurementId) {
        return getMetadataForMeasurement(UUID.fromString(measurementId));
    }

    public MeasurementMetadata getMetadataForMeasurement(UUID measurementId) {
        return measurementRepository.findById(measurementId)
                .map(m -> m.getMetadata())
                .map(m -> convertMetadataEntity(m))
                .orElseThrow(() -> new IllegalArgumentException("No measurement with id " + measurementId));
    }

    @NonNull
    private MeasurementMetadata convertMetadataEntity(@NonNull final MeasurementMetadataEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new MeasurementMetadata(entity.getId(), entity.getGitOriginUrl(),
                entity.getGitBranch(), entity.getGitCommitId(),
                entity.getGitTags(), entity.getGitDirty(),
                entity.getJmhThreadCount(), entity.getJmhForks(),
                entity.getJmhTimeout(), entity.getJmhWarmupIterations(),
                entity.getJmhWarmupTime(), entity.getJmhWarmupBatchSize(),
                entity.getJmhMeasurementIterations(), entity.getJmhMeasurementTime(),
                entity.getJmhMeasurementBatchSize(), entity.getSystemArch(),
                entity.getSystemProcessors(), entity.getSystemMemory(),
                entity.getOsName(), entity.getOsVersion(),
                entity.getJvmVersion(), entity.getJvmName(),
                entity.getJmhVersion());
    }

    public DateTimePeriode getPeriode(String benchmarkId) {
        Objects.requireNonNull(benchmarkId, "benchmarkId must not be null");
        return getPeriode(UUID.fromString(benchmarkId));
    }

    public DateTimePeriode getPeriode(UUID benchmarkId) {
        Objects.requireNonNull(benchmarkId, "benchmarkId must not be null");
        final Instant start = measurementRepository.findFirst(benchmarkId)
                .map(m -> m.getTimestamp())
                .orElseThrow(() -> new IllegalArgumentException("No measurement for benchmark " + benchmarkId));

        final Instant end = measurementRepository.findLast(benchmarkId)
                .map(m -> m.getTimestamp())
                .orElseThrow(() -> new IllegalArgumentException("No measurement for benchmark " + benchmarkId));
        return DateTimePeriode.between(start, end);
    }
}
