package com.openelements.benchscape.server.store.data;

import com.openelements.server.base.data.DataBase;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public record MeasurementMetadata(@Nullable UUID id, @Nullable String gitOriginUrl,
                                  @Nullable String gitBranch, @Nullable String gitCommitId,
                                  @NonNull Set<String> gitTags, @Nullable Boolean gitDirty,
                                  @Nullable Integer jmhThreadCount, @Nullable Integer jmhForks,
                                  @Nullable Duration jmhTimeout, @Nullable Integer jmhWarmupIterations,
                                  @Nullable Duration jmhWarmupTime, @Nullable Integer jmhWarmupBatchSize,
                                  @Nullable Integer jmhMeasurementIterations, @Nullable Duration jmhMeasurementTime,
                                  @Nullable Integer jmhMeasurementBatchSize, @Nullable String systemArch,
                                  @Nullable Integer systemProcessors, @Nullable Long systemMemory,
                                  @Nullable String osName, @Nullable String osVersion,
                                  @Nullable String jvmVersion, @Nullable String jvmName,
                                  @Nullable String jmhVersion, @NonNull Map<String, String> systemProperties,
                                  @NonNull Map<String, String> environmentProperties) implements DataBase {

    public MeasurementMetadata {
        Objects.requireNonNull(gitTags, "gitTags must not be null");
        Objects.requireNonNull(systemProperties, "systemProperties must not be null");
        Objects.requireNonNull(environmentProperties, "environmentProperties must not be null");
    }
}
