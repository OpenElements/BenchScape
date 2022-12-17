package com.openelements.jmh.store.shared;

import java.time.Instant;

public record Timeseries(Long id, Instant timestamp, double value, double error,
                         double min, double max, Integer availableProcessors,
                         Long memory, String jvmVersion) {

}
