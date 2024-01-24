package com.openelements.benchscape.server.store.data;

import edu.umd.cs.findbugs.annotations.Nullable;
import java.io.Serializable;

public record EnvironmentQuery(@Nullable String name,
                               @Nullable String gitOriginUrl,
                               @Nullable String gitBranch,
                               @Nullable String systemArch,
                               @Nullable Integer systemProcessors,
                               @Nullable Integer systemProcessorsMin,
                               @Nullable Integer systemProcessorsMax,
                               @Nullable Long systemMemory,
                               @Nullable Long systemMemoryMin,
                               @Nullable Long systemMemoryMax,
                               @Nullable String osName,
                               @Nullable String osVersion,
                               @Nullable String jvmVersion,
                               @Nullable String jvmName,
                               @Nullable String jmhVersion) implements Serializable {


}
