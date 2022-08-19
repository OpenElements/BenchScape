package com.example.demo;

import com.example.demo.data.BenchmarkEntity;
import com.example.demo.data.TimeseriesEntity;
import com.example.demo.repositories.BenchmarkRepository;
import com.example.demo.repositories.TimeseriesRepository;
import com.openelements.jmh.store.data.Benchmark;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BenchmarkEndpoint {

    private final BenchmarkRepository benchmarkRepository;

    private final TimeseriesRepository timeseriesRepository;

    @Autowired
    public BenchmarkEndpoint(final BenchmarkRepository benchmarkRepository, final TimeseriesRepository timeseriesRepository) {
        this.benchmarkRepository = benchmarkRepository;
        this.timeseriesRepository = timeseriesRepository;
    }

    @PostMapping("/benchmark")
    void storeBenchmark(@RequestBody final Benchmark benchmark) {
        final BenchmarkEntity benchmarkEntity = benchmarkRepository.findByName(benchmark.benchmark()).orElseGet(() -> {
            BenchmarkEntity newEntity = new BenchmarkEntity();
            newEntity.setName(benchmark.benchmark());
            newEntity.setUnit(benchmark.result().unit());
            return benchmarkRepository.save(newEntity);
        });
        final TimeseriesEntity timeseriesEntity = new TimeseriesEntity();
        timeseriesEntity.setBenchmarkId(benchmarkEntity.getId() + "");
        timeseriesEntity.setTimestamp(benchmark.execution().measurementTime());
        timeseriesEntity.setValue(benchmark.result().value());
        timeseriesEntity.setError(benchmark.result().error());
        timeseriesRepository.save(timeseriesEntity);
    }

    @GetMapping("/benchmarks")
    @ResponseBody
    List<String> getAllBenchmarks() {
        return benchmarkRepository.findAll().stream()
                .map(b -> b.getName())
                .collect(Collectors.toList());
    }

    @GetMapping("/timeseries")
    @ResponseBody
    List<TimeseriesEntity> getTimeseries(@RequestParam(name = "benchmark") String benchmark) {
        final String benchmarkId = benchmarkRepository.findByName(benchmark).orElseThrow().getId() + "";
        return timeseriesRepository.findAllForBenchmark(benchmarkId);
    }
}
