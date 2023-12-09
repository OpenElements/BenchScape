package com.openelements.benchscape.server.store.math;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.io.Serializable;
import java.time.Instant;

public record InterpolatedMeasurement(@NonNull Instant timestamp, double value,
                                      @Nullable Double error,
                                      @Nullable Double min, @Nullable Double max,
                                      @NonNull BenchmarkUnit unit) implements Serializable {
}
