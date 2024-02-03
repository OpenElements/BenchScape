package com.openelements.benchscape.server.store.data;

import java.io.Serializable;
import java.time.Instant;

/**
 * Data object of a date time periode.
 *
 * @param start start of the periode
 * @param end   end of the periode
 */
public record DateTimePeriode(Instant start, Instant end) implements Serializable {

    public static DateTimePeriode between(Instant start, Instant end) {
        return new DateTimePeriode(start, end);
    }
}
