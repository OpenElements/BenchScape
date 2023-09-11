package com.openelements.jmh.common;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Arrays;
import java.util.Objects;

/**
 * The unit of a benchmark.
 */
public enum BenchmarkUnit {
    OPERATIONS_PER_DAY("ops/day"),
    OPERATIONS_PER_HOUR("ops/hr"),
    OPERATIONS_PER_MINUTE("ops/min"),
    OPERATIONS_PER_SECOND("ops/s"),
    OPERATIONS_PER_MILLISECOND("ops/ms"),
    OPERATIONS_PER_NANOSECOND("ops/ns"),
    DAYS_PER_OPERATION("day/op"),
    HOURS_PER_OPERATION("hr/op"),
    MINUTES_PER_OPERATION("min/op"),
    SECONDS_PER_OPERATION("s/op"),
    MILLISECONDS_PER_OPERATION("ms/op"),
    NANOSECONDS_PER_OPERATION("ns/op"),
    UNKNWOWN("unknown");

    private final String readableName;

    BenchmarkUnit(@NonNull final String readableName) {
        this.readableName = readableName;
    }

    @NonNull
    public static BenchmarkUnit getForJmhName(@NonNull final String jmhUnitName) {
        Objects.requireNonNull(jmhUnitName, "jmhUnitName must not be null");
        return Arrays.asList(BenchmarkUnit.values()).stream()
                .filter(unit -> Objects.equals(unit.readableName, jmhUnitName))
                .findFirst()
                .orElse(UNKNWOWN);
    }

    public String getReadableName() {
        return readableName;
    }
}
