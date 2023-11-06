package com.openelements.benchscape.jmh.model;

import java.util.Arrays;
import java.util.Objects;

/**
 * The unit of a benchmark.
 */
public enum BenchmarkUnit {
    OPERATIONS_PER_DAY("ops/day", 1L),
    OPERATIONS_PER_HOUR("ops/hr", 24L),
    OPERATIONS_PER_MINUTE("ops/min", 24L * 60L),
    OPERATIONS_PER_SECOND("ops/s", 24L * 60L * 60L),
    OPERATIONS_PER_MILLISECOND("ops/ms", 24L * 60L * 60L * 1_000L),
    OPERATIONS_PER_NANOSECOND("ops/ns", 24L * 60L * 60L * 1_000L * 1_000_000L),
    DAYS_PER_OPERATION("day/op", 1_000_000L * 1_000L * 60L * 60L * 24L),
    HOURS_PER_OPERATION("hr/op", 1_000_000L * 1_000L * 60L * 60L),
    MINUTES_PER_OPERATION("min/op", 1_000_000L * 1_000L * 60L),
    SECONDS_PER_OPERATION("s/op", 1_000_000L * 1_000L),
    MILLISECONDS_PER_OPERATION("ms/op", 1_000_000L),
    NANOSECONDS_PER_OPERATION("ns/op", 1L),
    @Deprecated(forRemoval = true)
    UNKNWOWN("unknown", 0L);

    private final String readableName;

    private final long factor;

    BenchmarkUnit(final String readableName, final long factor) {
        this.readableName = readableName;
        this.factor = factor;
    }

    public static BenchmarkUnit getForJmhName(final String jmhUnitName) {
        Objects.requireNonNull(jmhUnitName, "jmhUnitName must not be null");
        return Arrays.asList(BenchmarkUnit.values()).stream()
                .filter(unit -> Objects.equals(unit.readableName, jmhUnitName))
                .findFirst()
                .orElse(UNKNWOWN);
    }

    public String getReadableName() {
        return readableName;
    }

    /**
     * Converts the given value of the given unit to this unit
     *
     * @param value
     * @param unit
     * @return
     */
    public double convert(final double value, final BenchmarkUnit unit) {
        Objects.requireNonNull(unit, "unit must not be null");
        if (this == UNKNWOWN || unit == UNKNWOWN) {
            throw new IllegalArgumentException("Can not convert for unknown unit");
        }
        if (isOperationsPerPeriode() == unit.isOperationsPerPeriode()) {
            final double valueInBase = value * unit.factor;
            return valueInBase / factor;
        } else {
            final double newValue = 1 / value;
            final BenchmarkUnit newUnit = getOpposite(unit);
            return convert(newValue, newUnit);
        }
    }

    private BenchmarkUnit getOpposite(final BenchmarkUnit unit) {
        Objects.requireNonNull(unit, "unit must not be null");
        if (this == UNKNWOWN || unit == UNKNWOWN) {
            throw new IllegalArgumentException("Can not convert for unknown unit");
        }
        if (unit == OPERATIONS_PER_DAY) {
            return DAYS_PER_OPERATION;
        }
        if (unit == OPERATIONS_PER_HOUR) {
            return HOURS_PER_OPERATION;
        }
        if (unit == OPERATIONS_PER_MINUTE) {
            return MINUTES_PER_OPERATION;
        }
        if (unit == OPERATIONS_PER_SECOND) {
            return SECONDS_PER_OPERATION;
        }
        if (unit == OPERATIONS_PER_MILLISECOND) {
            return MILLISECONDS_PER_OPERATION;
        }
        if (unit == OPERATIONS_PER_NANOSECOND) {
            return NANOSECONDS_PER_OPERATION;
        }
        if (unit == DAYS_PER_OPERATION) {
            return OPERATIONS_PER_DAY;
        }
        if (unit == HOURS_PER_OPERATION) {
            return OPERATIONS_PER_HOUR;
        }
        if (unit == MINUTES_PER_OPERATION) {
            return OPERATIONS_PER_MINUTE;
        }
        if (unit == SECONDS_PER_OPERATION) {
            return OPERATIONS_PER_SECOND;
        }
        if (unit == MILLISECONDS_PER_OPERATION) {
            return OPERATIONS_PER_MILLISECOND;
        }
        if (unit == NANOSECONDS_PER_OPERATION) {
            return OPERATIONS_PER_NANOSECOND;
        }
        throw new IllegalStateException("Can not convert for unknown unit");
    }

    private boolean isOperationsPerPeriode() {
        if (this == OPERATIONS_PER_DAY) {
            return true;
        }
        if (this == OPERATIONS_PER_HOUR) {
            return true;
        }
        if (this == OPERATIONS_PER_MINUTE) {
            return true;
        }
        if (this == OPERATIONS_PER_SECOND) {
            return true;
        }
        if (this == OPERATIONS_PER_MILLISECOND) {
            return true;
        }
        if (this == OPERATIONS_PER_NANOSECOND) {
            return true;
        }
        return false;
    }

}
