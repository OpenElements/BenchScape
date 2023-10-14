package com.openelements.jmh.store.v2.services;

import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import com.openelements.jmh.store.v2.entities.BenchmarkEntity;
import com.openelements.jmh.store.v2.entities.MeasurementEntity;
import com.openelements.jmh.store.v2.entities.MeasurementMetadataEntity;
import com.openelements.jmh.store.v2.repositories.BenchmarkRepository;
import com.openelements.jmh.store.v2.repositories.MeasurementMetadataRepository;
import com.openelements.jmh.store.v2.repositories.MeasurementRepository;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BenchmarkExecutionService {

    private final BenchmarkRepository benchmarkRepository;

    private final MeasurementRepository measurementRepository;

    private final MeasurementMetadataRepository measurementMetadataRepository;

    @Autowired
    public BenchmarkExecutionService(
            BenchmarkRepository benchmarkRepository, MeasurementRepository measurementRepository,
            MeasurementMetadataRepository measurementMetadataRepository) {
        this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository, "benchmarkRepository must not be null");
        this.measurementRepository = Objects.requireNonNull(measurementRepository,
                "measurementRepository must not be null");
        this.measurementMetadataRepository = Objects.requireNonNull(measurementMetadataRepository,
                "measurementMetadataRepository must not be null");
    }

    public void add(final BenchmarkExecution benchmarkExecution) {
        final UUID benchmarkEntityId = benchmarkRepository.findByName(benchmarkExecution.benchmarkName()).stream()
                .filter(b -> b.getParams().equals(benchmarkExecution.params()))
                .findFirst()
                .map(e -> e.getId())
                .orElseGet(() -> {
                    BenchmarkEntity benchmarkEntity = new BenchmarkEntity();
                    benchmarkEntity.setName(benchmarkExecution.benchmarkName());
                    benchmarkEntity.setParams(benchmarkExecution.params());
                    return benchmarkRepository.save(benchmarkEntity).getId();
                });

        MeasurementEntity measurementEntity = new MeasurementEntity();
        measurementEntity.setBenchmarkId(benchmarkEntityId);
        measurementEntity.setTimestamp(benchmarkExecution.execution().startTime());
        measurementEntity.setUnit(benchmarkExecution.result().unit());
        measurementEntity.setValue(benchmarkExecution.result().value());
        measurementEntity.setMin(benchmarkExecution.result().min());
        measurementEntity.setMax(benchmarkExecution.result().max());
        measurementEntity.setError(benchmarkExecution.result().error());
        final UUID measurementid = measurementRepository.save(measurementEntity).getId();

        MeasurementMetadataEntity metadataEntity = new MeasurementMetadataEntity();
        metadataEntity.setMeasurementId(measurementid);
        metadataEntity.setGitBranch(benchmarkExecution.gitState().branch());
        metadataEntity.setGitCommitId(benchmarkExecution.gitState().commitId());
        metadataEntity.setGitOriginUrl(benchmarkExecution.gitState().originUrl());
        metadataEntity.setGitDirty(benchmarkExecution.gitState().dirty());
        metadataEntity.setGitTags(benchmarkExecution.gitState().tags());
        measurementMetadataRepository.save(metadataEntity);
    }
}
