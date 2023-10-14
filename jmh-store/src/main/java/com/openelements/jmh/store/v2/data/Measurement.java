package com.openelements.jmh.store.v2.data;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public record Measurement(@Nullable UUID id, @NonNull Instant timestamp, double value, @Nullable Double error,
                          @Nullable Double min, @Nullable Double max,
                          @NonNull BenchmarkUnit unit) {

    public Measurement {
        Objects.requireNonNull(timestamp, "timestamp must not be null");
        Objects.requireNonNull(unit, "unit must not be null");
    }

    public Measurement withUnit(@NonNull BenchmarkUnit unit) {
        Objects.requireNonNull(unit, "unit must not be null");
        if (this.unit.equals(unit)) {
            return this;
        }
        return new Measurement(id, timestamp, unit.convert(value, this.unit), unit.convert(error, this.unit),
                unit.convert(min, this.unit), unit.convert(max, this.unit), unit);
    }
}
