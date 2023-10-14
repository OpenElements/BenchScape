package com.openelements.jmh.store.v2.services;

import com.openelements.jmh.store.v2.data.Benchmark;
import com.openelements.jmh.store.v2.repositories.BenchmarkRepository;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BenchmarkService {

    private final BenchmarkRepository benchmarkRepository;

    public BenchmarkService(BenchmarkRepository benchmarkRepository) {
        this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository, "benchmarkRepository must not be null");
    }

    public List<Benchmark> findAll() {
        return benchmarkRepository.findAll().stream()
                .map(e -> new Benchmark(e.getId(), e.getName(), Collections.unmodifiableMap(e.getParams())))
                .toList();
    }
}
