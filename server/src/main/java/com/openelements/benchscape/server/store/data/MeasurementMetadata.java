package com.openelements.benchscape.server.store.data;

import com.openelements.server.base.data.DataBase;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.time.Duration;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

/**
 * Data object of a measurement metadata. A measurement is splitted in two parts: metadata and data. The data can be
 * found at {@link Measurement}.
 *
 * @param id                       technical unique id of the measurement
 * @param gitOriginUrl             git origin url
 * @param gitBranch                git branch
 * @param gitCommitId              git commit id
 * @param gitTags                  git tags
 * @param gitDirty                 git dirty
 * @param jmhThreadCount           jmh thread count
 * @param jmhForks                 jmh forks
 * @param jmhTimeout               jmh timeout
 * @param jmhWarmupIterations      jmh warmup iterations
 * @param jmhWarmupTime            jmh warmup time
 * @param jmhWarmupBatchSize       jmh warmup batch size
 * @param jmhMeasurementIterations jmh measurement iterations
 * @param jmhMeasurementTime       jmh measurement time
 * @param jmhMeasurementBatchSize  jmh measurement batch size
 * @param systemArch               system architecture on that the measurement was executed
 * @param systemProcessors         system processors on that the measurement was executed
 * @param systemMemory             system memory on that the measurement was executed
 * @param osName                   os name on that the measurement was executed
 * @param osVersion                os version on that the measurement was executed
 * @param jvmVersion               jvm version on that the measurement was executed
 * @param jvmName                  jvm name on that the measurement was executed
 * @param jmhVersion               jmh version with that the measurement was executed
 * @param systemProperties         system properties with that the measurement was executed
 * @param environmentProperties    environment properties with that the measurement was executed
 */
public record MeasurementMetadata(@Nullable UUID id, @Nullable String gitOriginUrl,
                                  @Nullable String gitBranch, @Nullable String gitCommitId,
                                  @NonNull Set<String> gitTags, @Nullable Boolean gitDirty,
                                  @Nullable Integer jmhThreadCount, @Nullable Integer jmhForks,
                                  @Nullable Duration jmhTimeout, @Nullable Integer jmhWarmupIterations,
                                  @Nullable Duration jmhWarmupTime, @Nullable Integer jmhWarmupBatchSize,
                                  @Nullable Integer jmhMeasurementIterations, @Nullable Duration jmhMeasurementTime,
                                  @Nullable Integer jmhMeasurementBatchSize, @Nullable String systemArch,
                                  @Nullable Integer systemProcessors, @Nullable SystemMemory systemMemory,
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
