package com.openelements.benchscape.server.store.services;

import com.openelements.benchscape.server.store.data.Benchmark;
import com.openelements.benchscape.server.store.entities.BenchmarkEntity;
import com.openelements.benchscape.server.store.repositories.BenchmarkRepository;
import com.openelements.server.base.tenant.TenantService;
import com.openelements.server.base.tenantdata.AbstractServiceWithTenant;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Collections;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BenchmarkService extends AbstractServiceWithTenant<BenchmarkEntity, Benchmark> {

    private final BenchmarkRepository benchmarkRepository;

    private final TenantService tenantService;

    public BenchmarkService(final @NonNull BenchmarkRepository benchmarkRepository,
            final @NonNull TenantService tenantService) {
        this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository, "benchmarkRepository must not be null");
        this.tenantService = Objects.requireNonNull(tenantService, "tenantService must not be null");
    }

    @Override
    protected TenantService getTenantService() {
        return tenantService;
    }

    @NonNull
    @Override
    protected BenchmarkRepository getRepository() {
        return benchmarkRepository;
    }

    @NonNull
    @Override
    protected Benchmark mapToData(@NonNull final BenchmarkEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Benchmark(entity.getId(), entity.getName(), Collections.unmodifiableMap(entity.getParams()),
                Collections.unmodifiableList(entity.getTags()));
    }

    @NonNull
    @Override
    protected BenchmarkEntity updateEntity(@NonNull final BenchmarkEntity entity, @NonNull final Benchmark benchmark) {
        entity.setId(benchmark.id());
        entity.setName(benchmark.name());
        entity.setParams(Collections.unmodifiableMap(benchmark.params()));
        entity.setTags(Collections.unmodifiableList(benchmark.tags()));
        return entity;
    }

    @NonNull
    @Override
    protected BenchmarkEntity createNewEntity() {
        return new BenchmarkEntity();
    }
}
