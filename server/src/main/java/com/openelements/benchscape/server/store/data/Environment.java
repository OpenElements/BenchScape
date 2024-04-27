package com.openelements.benchscape.server.store.data;

import com.openelements.server.base.data.DataBase;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.Objects;
import java.util.UUID;

/**
 * Data object of an environment. Next to fixed values an environment can define min/max values. See
 * {@link #systemProcessorsMin} and {@link #systemProcessorsMax} for example.
 *
 * @param id                      technical unique id of the environment
 * @param name                    unique name of the environment
 * @param description             description of the environment
 * @param gitOriginUrl            git origin url of the environment
 * @param gitBranch               git branch of the environment
 * @param systemArch              system architecture of the environment
 * @param systemProcessors        system processors of the environment
 * @param systemProcessorsMin     min system processors of the environment
 * @param systemProcessorsMax     max system processors of the environment
 * @param systemMemory            system memory of the environment
 * @param systemMemoryMin         min system memory of the environment
 * @param systemMemoryMax         max system memory of the environment
 * @param osName                  os name of the environment
 * @param osVersion               os version of the environment
 * @param osFamily                os family of the environment
 * @param jvmVersion              jvm version of the environment
 * @param jvmName                 jvm name of the environment
 * @param jmhVersion              jmh version of the environment
 */
public record Environment(@Nullable UUID id, @NonNull String name,
                          @Nullable String description,
                          @Nullable String gitOriginUrl,
                          @Nullable String gitBranch, @Nullable String systemArch,
                          @Nullable Integer systemProcessors, @Nullable Integer systemProcessorsMin,
                          @Nullable Integer systemProcessorsMax, @Nullable SystemMemory systemMemory,
                          @Nullable SystemMemory systemMemoryMin, @Nullable SystemMemory systemMemoryMax,
                          @Nullable String osName, @Nullable String osVersion,
                          @Nullable OperationSystem osFamily,
                          @Nullable String jvmVersion, @Nullable String jvmName,
                          @Nullable String jmhVersion) implements DataBase {

    public Environment {
        Objects.requireNonNull(name, "name must not be null");

        if (systemProcessors != null && systemProcessors < 0) {
            throw new IllegalStateException("systemProcessors must be > 0");
        }
        if (systemProcessorsMin != null && systemProcessorsMin < 0) {
            throw new IllegalStateException("systemProcessorsMin must be > 0");
        }
        if (systemProcessorsMax != null && systemProcessorsMax < 0) {
            throw new IllegalStateException("systemProcessorsMin must be > 0");
        }
        if (systemMemoryMax != null && systemMemoryMin != null && systemMemoryMax.isLessThan(systemMemoryMin)) {
            throw new IllegalStateException("systemProcessorsMin must be <= systemMemoryMax");
        }
    }

}
