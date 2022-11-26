package com.openelements.jmh.common;

import java.util.concurrent.TimeUnit;

public record BenchmarkConfiguration(int threads, int forks, TimeUnit timeUnit, long timeout, TimeUnit timeoutUnit,
                                     BenchmarkMeasurementConfiguration measurementConfiguration,
                                     BenchmarkMeasurementConfiguration warmupConfiguration) {


}
