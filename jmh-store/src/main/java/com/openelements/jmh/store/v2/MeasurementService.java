package com.openelements.jmh.store.v2;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MeasurementService {

    private final MeasurementRepository measurementRepository;

    @Autowired
    public MeasurementService(@NonNull final MeasurementRepository measurementRepository) {
        this.measurementRepository = Objects.requireNonNull(measurementRepository,
                "measurementRepository must not be null");
    }

    @NonNull
    public List<Measurement> find(final @NonNull MeasurementQuery query) {
        Objects.requireNonNull(query, "query must not be null");
        return measurementRepository.find(query.benchmarkId(), query.start(), query.end())
                .stream()
                .filter(m -> isMatchingEnvironment(m, query.environmentIds()))
                .map(m -> map(m))
                .map(m -> m.withUnit(query.unit()))
                .toList();
    }

    @NonNull
    private Measurement map(@NonNull final MeasurementEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Measurement(entity.getId(), entity.getTimestamp(), entity.getValue(),
                entity.getError(), entity.getMin(), entity.getMax(), entity.getUnit());
    }

    private boolean isMatchingEnvironment(@NonNull MeasurementEntity entity,
            @NonNull Collection<String> environmentIds) {
        Objects.requireNonNull(entity, "entity must not be null");
        Objects.requireNonNull(environmentIds, "environmentIds must not be null");
        if (environmentIds.isEmpty()) {
            return true;
        }
        return false;
    }

}
