package com.openelements.jmh.store.store.services;

import com.openelements.jmh.store.store.data.Benchmark;
import com.openelements.jmh.store.store.entities.BenchmarkEntity;
import com.openelements.jmh.store.store.repositories.BenchmarkRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
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
    protected JpaRepository<BenchmarkEntity, UUID> getRepository() {
        return benchmarkRepository;
    }

    @NonNull
    @Override
    protected Benchmark map(@NonNull BenchmarkEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Benchmark(entity.getId(), entity.getName(), Collections.unmodifiableMap(entity.getParams()));
    }

    @NonNull
    @Override
    protected BenchmarkEntity mapToEntity(@NonNull Benchmark benchmark) {
        Objects.requireNonNull(benchmark, "benchmark must not be null");
        final BenchmarkEntity benchmarkEntity = new BenchmarkEntity();
        benchmarkEntity.setId(benchmark.id());
        benchmarkEntity.setName(benchmark.name());
        benchmarkEntity.setParams(Collections.unmodifiableMap(benchmark.params()));
        return benchmarkEntity;
    }
}
