package com.openelements.jmh.store.v2.data;

import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.UUID;

public record Environment(@Nullable UUID id, @Nullable String gitOriginUrl,
                          @Nullable String gitBranch, @Nullable String systemArch,
                          @Nullable Integer systemProcessors, @Nullable Integer systemProcessorsMin,
                          @Nullable Integer systemProcessorsMax, @Nullable Long systemMemory,
                          @Nullable Long systemMemoryMin, @Nullable Long systemMemoryMax,
                          @Nullable String osName, @Nullable String osVersion,
                          @Nullable String jvmVersion, @Nullable String jvmName,
                          @Nullable String jmhVersion) {
}
