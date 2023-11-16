package com.openelements.benchscape.jmh.model;

import jakarta.validation.constraints.NotNull;
import java.util.Objects;
import java.util.Set;

public record BenchmarkGitState(String originUrl, String branch, String commitId,
                                @NotNull Set<String> tags, boolean dirty) {
    public BenchmarkGitState {
        Objects.requireNonNull(tags, "tags must not be null");
    }
}
