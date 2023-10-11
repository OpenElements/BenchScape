package com.openelements.jmh.store.v2.services;

import com.openelements.jmh.store.v2.data.Measurement;
import com.openelements.jmh.store.v2.data.MeasurementMetadata;
import com.openelements.jmh.store.v2.data.MeasurementQuery;
import com.openelements.jmh.store.v2.entities.MeasurementEntity;
import com.openelements.jmh.store.v2.repositories.MeasurementRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    private final MeasurementMetadataService measurementMetadataService;

    private final EnvironmentService environmentService;

    @Autowired
    public MeasurementService(@NonNull final MeasurementRepository measurementRepository,
            @NonNull final MeasurementMetadataService measurementMetadataService,
            @NonNull final EnvironmentService environmentService) {
        this.measurementRepository = Objects.requireNonNull(measurementRepository,
                "measurementRepository must not be null");
        this.measurementMetadataService = Objects.requireNonNull(measurementMetadataService,
                "measurementMetadataService must not be null");
        this.environmentService = Objects.requireNonNull(environmentService,
                "environmentService must not be null");
    }

    @NonNull
    public List<Measurement> find(final @NonNull MeasurementQuery query) {
        Objects.requireNonNull(query, "query must not be null");
        return measurementRepository.find(query.benchmarkId(), query.start(), query.end())
                .stream()
                .map(m -> map(m))
                .filter(m -> isMatchingEnvironment(m, query.environmentIds()))
                .map(m -> m.withUnit(query.unit()))
                .toList();
    }

    @NonNull
    public Optional<Measurement> findPrevious(@NonNull final Measurement measurement) {
        Objects.requireNonNull(measurement, "measurement must not be null");
        final String id = Optional.ofNullable(measurement.id())
                .map(UUID::toString)
                .orElseThrow(() -> new IllegalArgumentException("measurement.id() must not be null"));
        return measurementRepository.findLastBefore(id, measurement.timestamp())
                .map(m -> map(m));
    }

    @NonNull
    public Optional<Measurement> findNext(@NonNull final Measurement measurement) {
        Objects.requireNonNull(measurement, "measurement must not be null");
        final String id = Optional.ofNullable(measurement.id())
                .map(UUID::toString)
                .orElseThrow(() -> new IllegalArgumentException("measurement.id() must not be null"));
        return measurementRepository.findFirstAfter(id, measurement.timestamp())
                .map(m -> map(m));
    }

    @NonNull
    private static Measurement map(@NonNull final MeasurementEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Measurement(entity.getId(), entity.getTimestamp(), entity.getValue(),
                entity.getError(), entity.getMin(), entity.getMax(), entity.getUnit());
    }

    private boolean isMatchingEnvironment(@NonNull Measurement measurement,
            @NonNull Collection<String> environmentIds) {
        Objects.requireNonNull(measurement, "measurement must not be null");
        Objects.requireNonNull(environmentIds, "environmentIds must not be null");
        if (environmentIds.isEmpty()) {
            return true;
        }
        final MeasurementMetadata metadata = measurementMetadataService.getMetadataForMeasurement(measurement);
        return environmentIds.stream()
                .map(id -> environmentService.isMatchingEnvironment(metadata, id))
                .filter(result -> !result)
                .findAny()
                .isPresent();
    }

}
