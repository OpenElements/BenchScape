package com.openelements.benchscape.server.store.services;

import com.openelements.benchscape.server.store.data.Benchmark;
import com.openelements.benchscape.server.store.entities.BenchmarkEntity;
import com.openelements.benchscape.server.store.repositories.BenchmarkRepository;
import com.openelements.server.base.data.AbstractService;
import com.openelements.server.base.data.EntityRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BenchmarkService extends AbstractService<BenchmarkEntity, Benchmark> {

    private final BenchmarkRepository benchmarkRepository;

    public BenchmarkService(final @NonNull BenchmarkRepository benchmarkRepository) {
        this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository, "benchmarkRepository must not be null");
    }

    public Optional<Benchmark> findByName(String name) {
        return benchmarkRepository.findByName(name)
                .map(e -> new Benchmark(e.getId(), e.getName(), Collections.unmodifiableMap(e.getParams())));
    }

    @NonNull
    @Override
    protected EntityRepository<BenchmarkEntity> getRepository() {
        return benchmarkRepository;
    }

    @NonNull
    @Override
    protected Benchmark mapToData(@NonNull BenchmarkEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Benchmark(entity.getId(), entity.getName(), Collections.unmodifiableMap(entity.getParams()));
    }

    @NonNull
    @Override
    protected BenchmarkEntity updateEntity(@NonNull BenchmarkEntity entity, @NonNull Benchmark benchmark) {
        entity.setId(benchmark.id());
        entity.setName(benchmark.name());
        entity.setParams(Collections.unmodifiableMap(benchmark.params()));
        return entity;
    }

    @NonNull
    @Override
    protected BenchmarkEntity createNewEntity() {
        return new BenchmarkEntity();
    }
}
