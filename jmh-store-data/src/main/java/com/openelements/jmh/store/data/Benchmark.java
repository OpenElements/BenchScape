package com.openelements.jmh.store.data;

public record Benchmark(String id, String benchmark, BenchmarkType type, BenchmarkInfrastructure infrastructure,
                        BenchmarkConfiguration benchmarkConfiguration, BenchmarkResult benchmarkResult) {

}
