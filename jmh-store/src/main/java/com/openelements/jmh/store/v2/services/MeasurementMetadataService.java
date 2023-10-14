package com.openelements.jmh.store.v2.services;

import com.openelements.jmh.store.v2.data.Measurement;
import com.openelements.jmh.store.v2.data.MeasurementMetadata;
import com.openelements.jmh.store.v2.entities.MeasurementMetadataEntity;
import com.openelements.jmh.store.v2.repositories.MeasurementMetadataRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MeasurementMetadataService {

    private final MeasurementMetadataRepository metadataRepository;

    @Autowired
    public MeasurementMetadataService(final @NonNull MeasurementMetadataRepository measurementMetadataRepository) {
        this.metadataRepository = Objects.requireNonNull(measurementMetadataRepository,
                "measurementMetadataRepository must not be null");
    }

    @NonNull
    public MeasurementMetadata getMetadataForMeasurement(@NonNull final String measurementId) {
        Objects.requireNonNull(measurementId, "measurementId must not be null");
        return getMetadataForMeasurement(UUID.fromString(measurementId));
    }

    @NonNull
    public MeasurementMetadata getMetadataForMeasurement(@NonNull final UUID measurementId) {
        Objects.requireNonNull(measurementId, "measurementId must not be null");
        final MeasurementMetadataEntity entity = metadataRepository.findByMeasurementId(measurementId.toString())
                .orElseThrow(
                        () -> new IllegalStateException("No metadata found for measurement of id " + measurementId));
        return new MeasurementMetadata(entity.getMeasurementId(), entity.getGitOriginUrl(), entity.getGitBranch(),
                entity.getGitCommitId(), entity.getGitTags(), entity.getGitDirty(), entity.getJmhThreadCount(),
                entity.getJmhForks(), entity.getJmhTimeout(), entity.getJmhWarmupIterations(),
                entity.getJmhWarmupTime(), entity.getJmhWarmupBatchSize(), entity.getJmhMeasurementIterations(),
                entity.getJmhMeasurementTime(), entity.getJmhMeasurementBatchSize(), entity.getSystemArch(),
                entity.getSystemProcessors(), entity.getSystemMemory(), entity.getOsName(), entity.getOsVersion(),
                entity.getJvmVersion(), entity.getJvmName(), entity.getJmhVersion());
    }

    @NonNull
    public MeasurementMetadata getMetadataForMeasurement(@NonNull final Measurement measurement) {
        Objects.requireNonNull(measurement, "measurement must not be null");
        final UUID id = measurement.id();
        return getMetadataForMeasurement(id);
    }
}
