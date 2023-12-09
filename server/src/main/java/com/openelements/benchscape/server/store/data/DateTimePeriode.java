package com.openelements.benchscape.server.store.data;

import java.io.Serializable;
import java.time.Instant;

public record DateTimePeriode(Instant start, Instant end) implements Serializable {

    public static DateTimePeriode between(Instant start, Instant end) {
        return new DateTimePeriode(start, end);
    }
}
