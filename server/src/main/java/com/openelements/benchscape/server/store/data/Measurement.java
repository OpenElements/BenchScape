package com.openelements.benchscape.server.store.data;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import com.openelements.server.base.data.DataBase;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public record Measurement(@Nullable UUID id, @NonNull Instant timestamp, double value, @Nullable Double error,
                          @Nullable Double min, @Nullable Double max,
                          @NonNull BenchmarkUnit unit) implements DataBase {

    public Measurement {
        Objects.requireNonNull(timestamp, "timestamp must not be null");
        Objects.requireNonNull(unit, "unit must not be null");

        if (value < 0.0d) {
            throw new IllegalArgumentException("value '" + value + "' must be >= 0.0d");
        }
        if (min != null) {
            if (value < min) {
                throw new IllegalStateException("value '" + value + "' must be >= min '" + min + "'");
            }
        }
        if (max != null) {
            if (value > max) {
                throw new IllegalStateException("value '" + value + "' must be <= max '" + max + "'");
            }
        }
        if (min != null && max != null) {
            if (min > max) {
                throw new IllegalStateException("min '" + min + "' must be <= max '" + max + "'");
            }
        }
    }

    public Measurement withUnit(@NonNull BenchmarkUnit unit) {
        Objects.requireNonNull(unit, "unit must not be null");
        if (this.unit.equals(unit)) {
            return this;
        }
        final double convertedValue = unit.convert(value, this.unit);
        final Double convertedError = Optional.ofNullable(error)
                .map(e -> unit.convert(e, this.unit))
                .orElse(null);
        final Double convertedMin = Optional.ofNullable(min)
                .map(e -> unit.convert(e, this.unit))
                .orElse(null);
        final Double convertedMax = Optional.ofNullable(max)
                .map(e -> unit.convert(e, this.unit))
                .orElse(null);
        return new Measurement(id, timestamp, convertedValue, convertedError,
                convertedMin, convertedMax, unit);
    }
}
