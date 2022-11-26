package com.openelements.jmh.common;

import java.util.concurrent.TimeUnit;

public record BenchmarkMeasurementConfiguration(int iterations, long time, TimeUnit timeUnit, int batchSize) {

}
