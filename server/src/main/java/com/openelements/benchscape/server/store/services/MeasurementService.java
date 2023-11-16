package com.openelements.benchscape.server.store.services;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import com.openelements.benchscape.server.store.data.Measurement;
import com.openelements.benchscape.server.store.data.MeasurementMetadata;
import com.openelements.benchscape.server.store.data.MeasurementQuery;
import com.openelements.benchscape.server.store.entities.MeasurementEntity;
import com.openelements.benchscape.server.store.entities.MeasurementMetadataEntity;
import com.openelements.benchscape.server.store.repositories.MeasurementRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.math3.analysis.interpolation.SplineInterpolator;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class MeasurementService extends AbstractService<MeasurementEntity, Measurement> {

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
                .map(m -> map(m))
                .filter(m -> isMatchingEnvironment(m, query.environmentIds()))
                .map(m -> m.withUnit(query.unit()))
                .toList();
    }

    @NonNull
    private List<Measurement> withSplineInterpolation(@NonNull final List<Measurement> realMeasurements,
            final int count) {
        Objects.requireNonNull(realMeasurements, "realMeasurements must not be null");
        final SplineInterpolator splineInterpolator = new SplineInterpolator();
        final double[] xTime = new double[realMeasurements.size()];
        final double[] yValue = new double[realMeasurements.size()];
        final double[] yMin = new double[realMeasurements.size()];
        final double[] yMax = new double[realMeasurements.size()];
        final double[] yError = new double[realMeasurements.size()];
        for (int i = 0; i < realMeasurements.size(); i++) {
            final Measurement measurement = realMeasurements.get(i);
            xTime[i] = measurement.timestamp().toEpochMilli();
            yValue[i] = BenchmarkUnit.OPERATIONS_PER_MILLISECOND.convert(measurement.value(), measurement.unit());
            yMin[i] = Optional.ofNullable(realMeasurements.get(i).min())
                    .map(m -> BenchmarkUnit.OPERATIONS_PER_MILLISECOND.convert(m, measurement.unit()))
                    .orElse(Double.NaN);
            yMax[i] = Optional.ofNullable(realMeasurements.get(i).min())
                    .map(m -> BenchmarkUnit.OPERATIONS_PER_MILLISECOND.convert(m, measurement.unit()))
                    .orElse(Double.NaN);
            yError[i] = Optional.ofNullable(realMeasurements.get(i).min())
                    .map(m -> BenchmarkUnit.OPERATIONS_PER_MILLISECOND.convert(m, measurement.unit()))
                    .orElse(Double.NaN);
        }

        final PolynomialSplineFunction valueFunction = splineInterpolator.interpolate(xTime, yValue);
        final PolynomialSplineFunction minFunction = splineInterpolator.interpolate(xTime, yMin);
        final PolynomialSplineFunction maxFunction = splineInterpolator.interpolate(xTime, yMax);
        final PolynomialSplineFunction errorFunction = splineInterpolator.interpolate(xTime, yError);

        final double step = (xTime[xTime.length - 1] - xTime[0]) / (count - 1);
        final double start = xTime[0];
        double millis = start;

        for (int i = 0; i < count; i++) {
            final double valueCalc = valueFunction.value(millis);
            final double minCalc = minFunction.value(millis);
            final double maxCalc = maxFunction.value(millis);
            final double errorCalc = errorFunction.value(millis);

            final Measurement interpolatedMeasurement = new Measurement(null, Instant.ofEpochMilli((long) millis),
                    valueCalc,
                    errorCalc,
                    minCalc, maxCalc, BenchmarkUnit.OPERATIONS_PER_MILLISECOND);
            millis += step;
        }

        return realMeasurements;
    }

    @NonNull
    public List<Measurement> find(final @NonNull MeasurementQuery query, final int maxResults) {
        final List<Measurement> all = find(query);
        if (all.size() <= maxResults) {
            return all;
        }
        return withSplineInterpolation(all, maxResults);
    }

    @NonNull
    public Optional<Measurement> findPrevious(@NonNull final Measurement measurement) {
        Objects.requireNonNull(measurement, "measurement must not be null");
        final UUID id = Optional.ofNullable(measurement.id())
                .orElseThrow(() -> new IllegalArgumentException("measurement.id() must not be null"));
        return measurementRepository.findLastBefore(id, measurement.timestamp())
                .map(m -> map(m));
    }

    @NonNull
    public Optional<Measurement> findNext(@NonNull final Measurement measurement) {
        Objects.requireNonNull(measurement, "measurement must not be null");
        final UUID id = Optional.ofNullable(measurement.id())
                .orElseThrow(() -> new IllegalArgumentException("measurement.id() must not be null"));
        return measurementRepository.findFirstAfter(id, measurement.timestamp())
                .map(m -> map(m));
    }

    @NonNull
    @Override
    protected JpaRepository<MeasurementEntity, UUID> getRepository() {
        return measurementRepository;
    }

    @NonNull
    protected Measurement map(@NonNull final MeasurementEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Measurement(entity.getId(), entity.getTimestamp(), entity.getValue(),
                entity.getError(), entity.getMin(), entity.getMax(), entity.getUnit());
    }

    @NonNull
    @Override
    protected MeasurementEntity mapToEntity(@NonNull Measurement measurement) {
        Objects.requireNonNull(measurement, "measurement must not be null");
        MeasurementEntity entity = new MeasurementEntity();
        entity.setId(measurement.id());
        entity.setTimestamp(measurement.timestamp());
        entity.setValue(measurement.value());
        entity.setError(measurement.error());
        entity.setMin(measurement.min());
        entity.setMax(measurement.max());
        entity.setUnit(measurement.unit());
        return entity;
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
}
