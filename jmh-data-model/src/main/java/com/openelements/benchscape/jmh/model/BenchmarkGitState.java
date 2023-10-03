package com.openelements.benchscape.jmh.model;

import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.Objects;
import java.util.Set;

public record BenchmarkGitState(@Nullable String originUrl, @Nullable String branch, @Nullable String commitId,
                                @NonNull Set<String> tags, boolean dirty) {
    public BenchmarkGitState {
        Objects.requireNonNull(tags, "tags must not be null");
    }
}
