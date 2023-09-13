package com.openelements.jmh.store.db;

import com.openelements.benchscape.common.BenchmarkExecution;
import com.openelements.benchscape.common.BenchmarkExecutionResult;
import com.openelements.jmh.store.db.entities.BenchmarkEntity;
import com.openelements.jmh.store.db.entities.RulesEntity;
import com.openelements.jmh.store.db.entities.TimeseriesEntity;
import com.openelements.jmh.store.db.repositories.BenchmarkRepository;
import com.openelements.jmh.store.db.repositories.RulesRepository;
import com.openelements.jmh.store.db.repositories.TimeseriesRepository;
import com.openelements.jmh.store.shared.BenchmarkDefinition;
import com.openelements.jmh.store.shared.BenchmarkWithTimeseriesResult;
import com.openelements.jmh.store.shared.Rule;
import com.openelements.jmh.store.shared.Timeseries;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataService {

    private final BenchmarkRepository benchmarkRepository;

    private final TimeseriesRepository timeseriesRepository;

    private final RulesRepository rulesRepository;

    @Autowired
    public DataService(final BenchmarkRepository benchmarkRepository,
            final TimeseriesRepository timeseriesRepository, final RulesRepository rulesRepository) {
        this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository);
        this.timeseriesRepository = Objects.requireNonNull(timeseriesRepository);
        this.rulesRepository = Objects.requireNonNull(rulesRepository);
    }

    public List<Timeseries> getAllTimeseriesForBenchmarks(final long benchmarkId) {
        return timeseriesRepository.findAllForBenchmark(benchmarkId)
                .stream().map(entity -> convert(entity))
                .collect(Collectors.toList());
    }

    public List<BenchmarkDefinition> getAllBenchmarks() {
        return benchmarkRepository.findAll().stream()
                .map(entity -> convert(entity))
                .collect(Collectors.toList());
    }

    public Optional<BenchmarkDefinition> getBenchmarkById(final long id) {
        return benchmarkRepository.findById(id).map(entity -> convert(entity));
    }

    public Optional<Timeseries> getTimeseriesById(final long id) {
        return timeseriesRepository.findById(id).map(entity -> convert(entity));
    }

    public Optional<BenchmarkDefinition> getBenchmarkByName(final String name) {
        return benchmarkRepository.findByName(name).map(entity -> convert(entity));
    }

    public Optional<Rule> getRuleForBenchmark(final long benchmarkId) {
        return rulesRepository.findForBenchmark(benchmarkId).map(entity -> convert(entity));
    }

    public BenchmarkWithTimeseriesResult save(final BenchmarkExecution benchmark) {
        Objects.requireNonNull(benchmark, "benchmark must not be null");
        final BenchmarkExecutionResult result = Objects.requireNonNull(benchmark.result(),
                "benchmark.result must not be null");

        final BenchmarkEntity benchmarkEntity = benchmarkRepository.findByName(benchmark.benchmarkName())
                .orElseGet(() -> {
                    final BenchmarkEntity newEntity = new BenchmarkEntity();
                    newEntity.setName(benchmark.benchmarkName());
                    newEntity.setUnit(result.unit());
                    Optional.ofNullable(benchmark.infrastructure()).ifPresent(infrastructure -> {
                        newEntity.setDefaultAvailableProcessors(infrastructure.availableProcessors());
                        newEntity.setDefaultJvmVersion(infrastructure.jvmVersion());
                        newEntity.setDefaultMemory(infrastructure.memory());
                    });
                    return benchmarkRepository.save(newEntity);
                });

        if (!Objects.equals(result.unit(), benchmarkEntity.getUnit())) {
            throw new IllegalArgumentException("The unit of the benchmark does not fit");
        }

        final TimeseriesEntity timeseriesEntity = new TimeseriesEntity();
        timeseriesEntity.setBenchmarkId(benchmarkEntity.getId());
        timeseriesEntity.setTimestamp(benchmark.execution().measurementTime());
        timeseriesEntity.setMeasurement(result.value());
        timeseriesEntity.setError(result.error());
        timeseriesEntity.setMin(result.min());
        timeseriesEntity.setMax(result.max());

        Optional.ofNullable(benchmark.infrastructure()).ifPresent(infrastructure -> {
            timeseriesEntity.setAvailableProcessors(infrastructure.availableProcessors());
            timeseriesEntity.setJvmVersion(infrastructure.jvmVersion());
            timeseriesEntity.setMemory(infrastructure.memory());
        });

        final TimeseriesEntity savedTimeseriesEntity = timeseriesRepository.save(timeseriesEntity);

        return new BenchmarkWithTimeseriesResult(benchmarkEntity.getId(),
                savedTimeseriesEntity.getId());
    }

    private BenchmarkDefinition convert(final BenchmarkEntity entity) {
        return new BenchmarkDefinition(entity.getId(), entity.getName(), entity.getUnit().name(),
                entity.getDefaultAvailableProcessors(), entity.getDefaultMemory(),
                entity.getDefaultJvmVersion());
    }

    private Timeseries convert(final TimeseriesEntity entity) {
        return new Timeseries(entity.getId(), entity.getTimestamp(), entity.getMeasurement(),
                entity.getError(), entity.getMin(), entity.getMax(), entity.getAvailableProcessors(),
                entity.getMemory(), entity.getJvmVersion());
    }

    private Rule convert(final RulesEntity entity) {
        return new Rule(entity.getId(), entity.getBenchmarkId(), entity.getMaxAllowedValue(),
                entity.getMinAllowedValue(), entity.getMaxAllowedError(),
                entity.getMaxAllowedErrorDeviation(), entity.getMaxAllowedValueDeviation(),
                entity.isFailOnDifferentProcessorCount(), entity.isFailOnDifferentMemorySize(),
                entity.isFailOnDifferentJvmVersion());
    }
}
