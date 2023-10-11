package com.openelements.jmh.store.v2.data;

import java.time.Duration;
import java.util.Set;
import java.util.UUID;

public record MeasurementMetadata(UUID id, String gitOriginUrl,
                                  String gitBranch, String gitCommitId,
                                  Set<String> gitTags, Boolean gitDirty,
                                  Integer jmhThreadCount, Integer jmhForks,
                                  Duration jmhTimeout, Integer jmhWarmupIterations,
                                  Duration jmhWarmupTime, Integer jmhWarmupBatchSize,
                                  Integer jmhMeasurementIterations, Duration jmhMeasurementTime,
                                  Integer jmhMeasurementBatchSize, String systemArch,
                                  Integer systemProcessors, Long systemMemory,
                                  String osName, String osVersion,
                                  String jvmVersion, String jvmName,
                                  String jmhVersion) {
}
