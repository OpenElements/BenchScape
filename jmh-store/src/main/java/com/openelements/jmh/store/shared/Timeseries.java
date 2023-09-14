package com.openelements.jmh.store.shared;

import java.time.Instant;

public record Timeseries(Long id, Instant timestamp, double value, Double error,
                         Double min, Double max, Integer availableProcessors,
                         Long memory, String jvmVersion) {

}
