package com.openelements.jmh.store.shared;

import java.time.Instant;

public record TimeseriesDefinition(Long id, Instant timestamp, double value, double error,
                                   double min, double max) {

}
