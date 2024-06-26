package com.openelements.benchscape.jmh.model;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;

/**
 * The result of a benchmark execution. The performance is defined by the value and the unit. A result can be for
 * example 300 ops/s.
 *
 * @param value the value of the benchmark execution. It defined in the unit that is provided by {@link #unit}.
 * @param error the error of the benchmark execution or null if now error was determinated. It defined in the unit that
 *              is provided by {@link #unit}.
 * @param min   the minimum value of the benchmark execution or null if now error was determinated. It defined in the
 *              unit that is provided by {@link #unit}.
 * @param max   the maximum value of the benchmark execution or null if now error was determinated. It defined in the
 *              unit that is provided by {@link #unit}.
 * @param unit  the unit of the benchmark execution.
 */
public record BenchmarkExecutionResult(double value, Double error, @NotNull Double min, Double max,
                                       @NotNull BenchmarkUnit unit) {

    public BenchmarkExecutionResult {
        Objects.requireNonNull(value, "value must not be null");
        Objects.requireNonNull(unit, "unit must not be null");
    }
}
