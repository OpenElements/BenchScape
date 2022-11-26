package com.openelements.jmh.common;

public record Benchmark(String id, String benchmark, BenchmarkType type, BenchmarkInfrastructure infrastructure,
                        BenchmarkConfiguration configuration, BenchmarkExecution execution, Result result) {

}
