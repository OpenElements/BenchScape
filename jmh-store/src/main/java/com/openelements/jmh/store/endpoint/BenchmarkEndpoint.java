package com.openelements.jmh.store.endpoint;

import com.openelements.benchscape.common.BenchmarkExecution;
import com.openelements.jmh.store.db.DataService;
import com.openelements.jmh.store.gate.QualityGate;
import com.openelements.jmh.store.shared.BenchmarkDefinition;
import com.openelements.jmh.store.shared.BenchmarkWithTimeseriesResult;
import com.openelements.jmh.store.shared.Timeseries;
import com.openelements.jmh.store.shared.Violation;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api") // Base path for all endpoints
@Api(tags = "Benchmark API") // Swagger API tags
public class BenchmarkEndpoint {

    private final DataService dataService;

    private final QualityGate qualityGate;

    @Autowired
    public BenchmarkEndpoint(final DataService dataService, final QualityGate qualityGate) {
        this.dataService = Objects.requireNonNull(dataService);
        this.qualityGate = Objects.requireNonNull(qualityGate);
    }

    @ApiOperation("Store benchmark")
    @CrossOrigin
    @PostMapping("/benchmark")
    public StoreBenchmarkResult storeBenchmark(@RequestBody final BenchmarkExecution benchmark) {
        final BenchmarkWithTimeseriesResult result = dataService.save(benchmark);

        final BenchmarkDefinition benchmarkDefinition = dataService.getBenchmarkById(
                        result.benchmarkId())
                .orElseThrow(() -> new IllegalStateException());
        final Timeseries timeseries = dataService.getTimeseriesById(result.timeseriesId())
                .orElseThrow(() -> new IllegalStateException());

        final Set<Violation> checkResult = qualityGate.check(benchmarkDefinition, timeseries);
        return new StoreBenchmarkResult(benchmarkDefinition.id(), timeseries.id(), checkResult);
    }

    @ApiOperation("Get all benchmarks")
    @CrossOrigin
    @GetMapping("/benchmark")
    public List<BenchmarkDefinition> getAllBenchmarks() {
        return dataService.getAllBenchmarks();
    }

    @ApiOperation("Get benchmark by ID")
    @CrossOrigin
    @GetMapping("/benchmark/{id}")
    public BenchmarkDefinition getBenchmarkById(@PathVariable final Long id) {
        return dataService.getBenchmarkById(id)
                .orElseThrow(() -> new IllegalArgumentException("Not found!"));
    }
}
