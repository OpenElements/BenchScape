package com.openelements.jmh.common;

import java.time.Instant;

public record BenchmarkExecution(Instant startTime, Instant warmupTime, Instant measurementTime, Instant stopTime,
                                 long warmupOps, long measurementOps) {
}
