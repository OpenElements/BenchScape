package com.openelements.benchscape.server.store.services;

import com.openelements.benchscape.server.store.data.DateTimePeriode;
import com.openelements.benchscape.server.store.data.Measurement;
import com.openelements.benchscape.server.store.data.MeasurementMetadata;
import com.openelements.benchscape.server.store.data.MeasurementQuery;
import com.openelements.benchscape.server.store.data.SystemMemory;
import com.openelements.benchscape.server.store.entities.MeasurementEntity;
import com.openelements.benchscape.server.store.entities.MeasurementMetadataEntity;
import com.openelements.benchscape.server.store.repositories.MeasurementRepository;
import com.openelements.server.base.tenant.TenantService;
import com.openelements.server.base.tenantdata.AbstractServiceWithTenant;
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
public class MeasurementService extends AbstractServiceWithTenant<MeasurementEntity, Measurement> {

    private final MeasurementRepository measurementRepository;

    private final EnvironmentService environmentService;

    private final TenantService tenantService;

    @Autowired
    public MeasurementService(@NonNull final MeasurementRepository measurementRepository,
            @NonNull final EnvironmentService environmentService,
            @NonNull final TenantService tenantService) {
        this.measurementRepository = Objects.requireNonNull(measurementRepository,
                "measurementRepository must not be null");
        this.environmentService = Objects.requireNonNull(environmentService,
                "environmentService must not be null");
        this.tenantService = Objects.requireNonNull(tenantService, "tenantService must not be null");
    }

    @Override
    @NonNull
    protected TenantService getTenantService() {
        return tenantService;
    }

    @NonNull
    public List<Measurement> findAllForBenchmark(@NonNull final String benchmarkId) {
        Objects.requireNonNull(benchmarkId, "benchmarkId must not be null");
        return measurementRepository.findByBenchmarkId(UUID.fromString(benchmarkId))
                .stream()
                .map(m -> mapToData(m))
                .toList();
    }

    @NonNull
    public List<Measurement> find(@NonNull final MeasurementQuery query) {
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
    protected MeasurementRepository getRepository() {
        return measurementRepository;
    }

    @NonNull
    @Override
    protected Measurement mapToData(@NonNull final MeasurementEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Measurement(entity.getId(), entity.getTimestamp(), entity.getValue(),
                entity.getError(), entity.getMin(), entity.getMax(), entity.getUnit(), entity.getComment());

    }

    @NonNull
    @Override
    protected MeasurementEntity updateEntity(@NonNull final MeasurementEntity entity,
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
            @NonNull final Collection<String> environmentIds) {
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

    @NonNull
    public MeasurementMetadata getMetadataForMeasurement(@NonNull final String measurementId) {
        return getMetadataForMeasurement(UUID.fromString(measurementId));
    }

    @NonNull
    public MeasurementMetadata getMetadataForMeasurement(@NonNull final UUID measurementId) {
        return measurementRepository.findByIdAndTenantId(measurementId, getCurrentTenantId())
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
                entity.getSystemProcessors(), SystemMemory.getFromByteConverter().apply(entity.getSystemMemory()),
                entity.getOsName(), entity.getOsVersion(),
                entity.getJvmVersion(), entity.getJvmName(),
                entity.getJmhVersion(), entity.getSystemProperties(), entity.getEnvironmentProperties());
    }

    @NonNull
    public DateTimePeriode getPeriode(@NonNull final String benchmarkId) {
        Objects.requireNonNull(benchmarkId, "benchmarkId must not be null");
        return getPeriode(UUID.fromString(benchmarkId));
    }

    @NonNull
    public DateTimePeriode getPeriode(@NonNull final UUID benchmarkId) {
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
