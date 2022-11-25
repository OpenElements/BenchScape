package com.openelements.jmh.store.data;

import java.util.concurrent.TimeUnit;

public record BenchmarkConfiguration(int threads, int forks, TimeUnit timeUnit, long timeout, TimeUnit timeoutUnit,
                                     BenchmarkMeasurementConfiguration measurementConfiguration,
                                     BenchmarkMeasurementConfiguration warmupConfiguration) {


}
