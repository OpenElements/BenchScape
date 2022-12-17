package com.openelements.jmh.store.endpoint;

import com.openelements.jmh.common.Benchmark;
import com.openelements.jmh.store.db.DataService;
import com.openelements.jmh.store.gate.QualityGate;
import com.openelements.jmh.store.shared.BenchmarkDefinition;
import com.openelements.jmh.store.shared.BenchmarkWithTimeseriesResult;
import com.openelements.jmh.store.shared.Timeseries;
import com.openelements.jmh.store.shared.Violation;
import java.util.List;
import java.util.Objects;
import java.util.Set;
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

  private final DataService dataService;

  private final QualityGate qualityGate;

  @Autowired
  public BenchmarkEndpoint(final DataService dataService, final QualityGate qualityGate) {
    this.dataService = Objects.requireNonNull(dataService);
    this.qualityGate = Objects.requireNonNull(qualityGate);
  }

  @CrossOrigin
  @PostMapping("/benchmark")
  StoreBenchmarkResult storeBenchmark(@RequestBody final Benchmark benchmark) {
    final BenchmarkWithTimeseriesResult result = dataService.save(benchmark);

    final BenchmarkDefinition benchmarkDefinition = dataService.getBenchmarkById(
            result.benchmarkId())
        .orElseThrow(() -> new IllegalStateException());
    final Timeseries timeseries = dataService.getTimeseriesById(result.timeseriesId())
        .orElseThrow(() -> new IllegalStateException());

    final Set<Violation> checkResult = qualityGate.check(benchmarkDefinition, timeseries);
    return new StoreBenchmarkResult(benchmarkDefinition.id(), timeseries.id(), checkResult);
  }

  @CrossOrigin
  @GetMapping("/benchmark")
  @ResponseBody
  List<BenchmarkDefinition> getAllBenchmarks() {
    return dataService.getAllBenchmarks();
  }

  @CrossOrigin
  @GetMapping("/benchmark/{id}")
  @ResponseBody
  BenchmarkDefinition getBenchmarkById(@PathVariable final Long id) {
    return dataService.getBenchmarkById(id)
        .orElseThrow(() -> new IllegalArgumentException("Not found!"));
  }

}
