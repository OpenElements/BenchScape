package com.openelements.jmh.store.store.endpoints;

import com.openelements.jmh.store.store.entities.BenchmarkEntity;
import com.openelements.jmh.store.store.entities.EnvironmentEntity;
import com.openelements.jmh.store.store.entities.MeasurementEntity;
import com.openelements.jmh.store.store.entities.MeasurementMetadataEntity;
import com.openelements.jmh.store.store.repositories.BenchmarkRepository;
import com.openelements.jmh.store.store.repositories.EnvironmentRepository;
import com.openelements.jmh.store.store.repositories.MeasurementMetadataRepository;
import com.openelements.jmh.store.store.repositories.MeasurementRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/debug")
public class DebugEndpoint {

    private final BenchmarkRepository benchmarkRepository;

    private final EnvironmentRepository environmentRepository;

    private final MeasurementMetadataRepository measurementMetadataRepository;

    private final MeasurementRepository measurementRepository;

    @Autowired
    public DebugEndpoint(@NonNull final BenchmarkRepository benchmarkRepository,
            @NonNull final EnvironmentRepository environmentRepository,
            @NonNull final MeasurementMetadataRepository measurementMetadataRepository,
            @NonNull final MeasurementRepository measurementRepository) {
        this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository, "benchmarkRepository must not be null");
        this.environmentRepository = Objects.requireNonNull(environmentRepository,
                "environmentRepository must not be null");
        this.measurementMetadataRepository = Objects.requireNonNull(measurementMetadataRepository,
                "measurementMetadataRepository must not be null");
        this.measurementRepository = Objects.requireNonNull(measurementRepository,
                "measurementRepository must not be null");
    }

    @GetMapping("/benchmarks")
    public List<BenchmarkEntity> getAllBenchmarkEntities() {
        return benchmarkRepository.findAll();
    }

    @GetMapping("/environments")
    public List<EnvironmentEntity> getAllEnvironmentEntities() {
        return environmentRepository.findAll();
    }

    @GetMapping("/measurementmetadata")
    public List<MeasurementMetadataEntity> getAllMeasurementMetadataEntities() {
        return measurementMetadataRepository.findAll();
    }

    @GetMapping("/measurements")
    public List<MeasurementEntity> getAllMeasurementEntities() {
        return measurementRepository.findAll().stream()
                .peek(e -> e.setMetadata(null))
                .collect(Collectors.toList());
    }
}
