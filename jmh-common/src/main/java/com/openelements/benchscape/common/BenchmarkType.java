package com.openelements.benchscape.common;

import edu.umd.cs.findbugs.annotations.NonNull;

/**
 * The type of a benchmark.
 */
public enum BenchmarkType {
    THROUGHPUT("Throughput");

    private final String readableName;

    BenchmarkType(@NonNull final String readableName) {
        this.readableName = readableName;
    }

    public String getReadableName() {
        return readableName;
    }
}
