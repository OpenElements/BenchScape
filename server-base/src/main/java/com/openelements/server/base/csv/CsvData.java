package com.openelements.server.base.csv;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record CsvData<D>(@NonNull List<CsvColumn<D>> columns) {

    public CsvData {
        Objects.requireNonNull(columns, "columns must not be null");
    }

    @NonNull
    public String[] getHeader() {
        return columns().stream()
                .map(CsvColumn::header)
                .collect(Collectors.toList())
                .toArray(new String[0]);
    }

    @NonNull
    public Stream<String> getData(@NonNull D data) {
        Objects.requireNonNull(data, "data must not be null");
        return columns().stream()
                .map(c -> c.extractForHeader().apply(data, c.header()));
    }
}