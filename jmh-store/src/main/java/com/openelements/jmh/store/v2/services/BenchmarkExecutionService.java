package com.openelements.jmh.store.v2.services;

import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import com.openelements.jmh.store.v2.entities.BenchmarkEntity;
import com.openelements.jmh.store.v2.entities.MeasurementEntity;
import com.openelements.jmh.store.v2.entities.MeasurementMetadataEntity;
import com.openelements.jmh.store.v2.repositories.BenchmarkRepository;
import com.openelements.jmh.store.v2.repositories.MeasurementMetadataRepository;
import com.openelements.jmh.store.v2.repositories.MeasurementRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Duration;
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
            final @NonNull BenchmarkRepository benchmarkRepository,
            final @NonNull MeasurementRepository measurementRepository,
            final @NonNull MeasurementMetadataRepository measurementMetadataRepository) {
        this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository, "benchmarkRepository must not be null");
        this.measurementRepository = Objects.requireNonNull(measurementRepository,
                "measurementRepository must not be null");
        this.measurementMetadataRepository = Objects.requireNonNull(measurementMetadataRepository,
                "measurementMetadataRepository must not be null");
    }

    public void add(final @NonNull BenchmarkExecution benchmarkExecution) {
        Objects.requireNonNull(benchmarkExecution, "benchmarkExecution must not be null");
        final UUID benchmarkEntityId = benchmarkRepository.findByName(benchmarkExecution.benchmarkName()).stream()
                .filter(b -> b.getParams().equals(benchmarkExecution.parameters()))
                .findFirst()
                .map(e -> e.getId())
                .orElseGet(() -> {
                    BenchmarkEntity benchmarkEntity = new BenchmarkEntity();
                    benchmarkEntity.setName(benchmarkExecution.benchmarkName());
                    benchmarkEntity.setParams(benchmarkExecution.parameters());
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
        final UUID measurementId = measurementRepository.save(measurementEntity).getId();

        MeasurementMetadataEntity metadataEntity = new MeasurementMetadataEntity();
        metadataEntity.setMeasurementId(measurementId);
        metadataEntity.setGitBranch(benchmarkExecution.gitState().branch());
        metadataEntity.setGitCommitId(benchmarkExecution.gitState().commitId());
        metadataEntity.setGitOriginUrl(benchmarkExecution.gitState().originUrl());
        metadataEntity.setGitDirty(benchmarkExecution.gitState().dirty());
        metadataEntity.setGitTags(benchmarkExecution.gitState().tags());
        metadataEntity.setJmhForks(benchmarkExecution.configuration().forks());
        metadataEntity.setJmhThreadCount(benchmarkExecution.configuration().threads());
        final long timeoutMillis = benchmarkExecution.configuration().timeoutUnit()
                .toMillis(benchmarkExecution.configuration().timeout());
        metadataEntity.setJmhTimeout(Duration.ofMillis(timeoutMillis));
        metadataEntity.setJmhVersion(benchmarkExecution.infrastructure().jmhVersion());
        metadataEntity.setJmhWarmupBatchSize(benchmarkExecution.configuration().warmupConfiguration().batchSize());
        metadataEntity.setJmhWarmupIterations(benchmarkExecution.configuration().warmupConfiguration().iterations());
        final long warmupMillis = benchmarkExecution.configuration().warmupConfiguration().timeUnit()
                .toMillis(benchmarkExecution.configuration().warmupConfiguration().time());
        metadataEntity.setJmhWarmupTime(Duration.ofMillis(warmupMillis));
        metadataEntity.setJmhMeasurementBatchSize(
                benchmarkExecution.configuration().measurementConfiguration().batchSize());
        metadataEntity.setJmhMeasurementIterations(
                benchmarkExecution.configuration().measurementConfiguration().iterations());
        final long measurementMillis = benchmarkExecution.configuration().measurementConfiguration().timeUnit()
                .toMillis(
                        benchmarkExecution.configuration().measurementConfiguration().time());
        metadataEntity.setJmhMeasurementTime(Duration.ofMillis(measurementMillis));
        metadataEntity.setJvmName(benchmarkExecution.infrastructure().jvmName());
        metadataEntity.setJvmVersion(benchmarkExecution.infrastructure().jvmVersion());
        metadataEntity.setOsName(benchmarkExecution.infrastructure().osName());
        metadataEntity.setOsVersion(benchmarkExecution.infrastructure().osVersion());
        metadataEntity.setSystemArch(benchmarkExecution.infrastructure().arch());
        metadataEntity.setSystemMemory(benchmarkExecution.infrastructure().memory());
        metadataEntity.setSystemProcessors(benchmarkExecution.infrastructure().availableProcessors());
        measurementMetadataRepository.save(metadataEntity);
    }
}
