package com.openelements.jmh.store.v2.data;

import java.util.UUID;

public record Environment(UUID id, String gitOriginUrl,
                          String gitBranch, String systemArch,
                          Integer systemProcessors, Integer systemProcessorsMin,
                          Integer systemProcessorsMax, Long systemMemory,
                          Long systemMemoryMin, Long systemMemoryMax,
                          String osName, String osVersion,
                          String jvmVersion, String jvmName,
                          String jmhVersion) {
}
