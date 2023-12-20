package com.openelements.benchscape.server.store.services;

import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import com.openelements.benchscape.server.store.entities.BenchmarkEntity;
import com.openelements.benchscape.server.store.entities.MeasurementEntity;
import com.openelements.benchscape.server.store.entities.MeasurementMetadataEntity;
import com.openelements.benchscape.server.store.repositories.BenchmarkRepository;
import com.openelements.benchscape.server.store.repositories.MeasurementRepository;
import com.openelements.server.base.tenant.TenantService;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Duration;
import java.util.Collections;
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

    private final TenantService tenantService;

    @Autowired
    public BenchmarkExecutionService(
            final @NonNull BenchmarkRepository benchmarkRepository,
            final @NonNull MeasurementRepository measurementRepository, TenantService tenantService) {
        this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository, "benchmarkRepository must not be null");
        this.measurementRepository = Objects.requireNonNull(measurementRepository,
                "measurementRepository must not be null");
        this.tenantService = Objects.requireNonNull(tenantService, "tenantService must not be null");
    }

    public void store(final @NonNull BenchmarkExecution benchmarkExecution) {
        Objects.requireNonNull(benchmarkExecution, "benchmarkExecution must not be null");
        final UUID benchmarkEntityId = benchmarkRepository.findByNameAndTenantId(benchmarkExecution.benchmarkName(),
                        tenantService.getCurrentTenant()).stream()
                .filter(b -> b.getParams().equals(benchmarkExecution.parameters()))
                .map(e -> e.getId())
                .findAny()
                .orElseGet(() -> {
                    BenchmarkEntity benchmarkEntity = new BenchmarkEntity();
                    benchmarkEntity.setName(benchmarkExecution.benchmarkName());
                    benchmarkEntity.setParams(benchmarkExecution.parameters());
                    benchmarkEntity.setTenantId(tenantService.getCurrentTenant());
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
        measurementEntity.setTenantId(tenantService.getCurrentTenant());

        MeasurementMetadataEntity metadataEntity = new MeasurementMetadataEntity();
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
        metadataEntity.setSystemProperties(
                Collections.unmodifiableMap(benchmarkExecution.infrastructure().systemProperties()));
        metadataEntity.setEnvironmentProperties(Collections.unmodifiableMap(benchmarkExecution.infrastructure()
                .environmentProperties()));
        metadataEntity.setTenantId(tenantService.getCurrentTenant());
        measurementEntity.setMetadata(metadataEntity);
        measurementRepository.save(measurementEntity);
    }
}
