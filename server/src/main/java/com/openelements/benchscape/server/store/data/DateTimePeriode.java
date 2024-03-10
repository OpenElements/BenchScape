package com.openelements.benchscape.server.store.data;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Data object of a date time periode.
 *
 * @param start start of the periode
 * @param end   end of the periode
 */
public record DateTimePeriode(@NonNull Instant start, @NonNull Instant end) implements Serializable {

    public DateTimePeriode {
        Objects.requireNonNull(start, "start must not be null");
        Objects.requireNonNull(end, "end must not be null");
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("start must be before end");
        }
    }

    public static DateTimePeriode between(@NonNull final Instant start, @NonNull final Instant end) {
        return new DateTimePeriode(start, end);
    }
}
