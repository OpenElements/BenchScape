package com.openelements.server.base.csv;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Objects;
import java.util.function.BiFunction;

public record CsvColumn<D>(@NonNull String header, @NonNull BiFunction<D, String, String> extractForHeader) {

    public CsvColumn {
        Objects.requireNonNull(header, "header must not be null");
        Objects.requireNonNull(extractForHeader, "extractForHeader must not be null");
    }
}
