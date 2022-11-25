package com.openelements.jmh.store.endpoint;

import com.openelements.jmh.store.data.Benchmark;
import com.openelements.jmh.store.db.entities.BenchmarkEntity;
import com.openelements.jmh.store.db.entities.TimeseriesEntity;
import com.openelements.jmh.store.db.repositories.BenchmarkRepository;
import com.openelements.jmh.store.db.repositories.TimeseriesRepository;
import com.openelements.jmh.store.shared.BenchmarkDefinition;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BenchmarkEndpoint {

  private final BenchmarkRepository benchmarkRepository;

  private final TimeseriesRepository timeseriesRepository;

  @Autowired
  public BenchmarkEndpoint(final BenchmarkRepository benchmarkRepository,
      final TimeseriesRepository timeseriesRepository) {
    this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository,
        "benchmarkRepository must not be null");
    this.timeseriesRepository = Objects.requireNonNull(timeseriesRepository,
        "timeseriesRepository must not be null");
  }

  @CrossOrigin
  @PostMapping("/benchmark")
  BenchmarkWithTimeseriesResult storeBenchmark(@RequestBody final Benchmark benchmark) {
    Objects.requireNonNull(benchmark, "benchmark must not be null");
    final BenchmarkEntity benchmarkEntity = benchmarkRepository.findByName(benchmark.benchmark())
        .orElseGet(() -> {
          final BenchmarkEntity newEntity = new BenchmarkEntity();
          newEntity.setName(benchmark.benchmark());
          newEntity.setUnit(benchmark.result().unit());
          return benchmarkRepository.save(newEntity);
        });

    if (!Objects.equals(benchmark.result().unit(), benchmarkEntity.getUnit())) {
      throw new IllegalArgumentException("The unit of the benchmark does not fit");
    }

    final TimeseriesEntity timeseriesEntity = new TimeseriesEntity();
    timeseriesEntity.setBenchmarkId(benchmarkEntity.getId());
    timeseriesEntity.setTimestamp(benchmark.execution().measurementTime());
    timeseriesEntity.setMeasurement(benchmark.result().value());
    timeseriesEntity.setError(benchmark.result().error());
    timeseriesEntity.setMin(benchmark.result().min());
    timeseriesEntity.setMax(benchmark.result().max());
    TimeseriesEntity savedTimeseriesEntity = timeseriesRepository.save(timeseriesEntity);
    
    return new BenchmarkWithTimeseriesResult(benchmarkEntity.getId(),
        savedTimeseriesEntity.getId());
  }

  @CrossOrigin
  @GetMapping("/benchmark")
  @ResponseBody
  List<BenchmarkDefinition> getAllBenchmarks() {
    return benchmarkRepository.findAll().stream()
        .map(entity -> convert(entity))
        .collect(Collectors.toList());
  }

  @CrossOrigin
  @GetMapping("/benchmark/{id}")
  @ResponseBody
  BenchmarkDefinition getBenchmarkById(@PathVariable final Long id) {
    return benchmarkRepository.findById(id)
        .map(entity -> convert(entity))
        .orElseThrow(() -> new IllegalArgumentException("No Benchmark with Id '" + id + "' found"));
  }

  private BenchmarkDefinition convert(final BenchmarkEntity entity) {
    return new BenchmarkDefinition(entity.getId(), entity.getName(), entity.getUnit());
  }
}
