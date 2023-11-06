package com.openelements.benchscape.jmh.model;

import jakarta.validation.constraints.NotNull;

/**
 * The type of a benchmark.
 */
public enum BenchmarkType {
    THROUGHPUT("Throughput");

    private final String readableName;

    BenchmarkType(@NotNull final String readableName) {
        this.readableName = readableName;
    }

    public String getReadableName() {
        return readableName;
    }
}
