package com.openelements.benchscape.server.store.data;

import com.openelements.server.base.data.DataBase;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.Objects;
import java.util.UUID;

public record Environment(@Nullable UUID id, @NonNull String name,
                          @Nullable String description,
                          @Nullable String gitOriginUrl,
                          @Nullable String gitBranch, @Nullable String systemArch,
                          @Nullable Integer systemProcessors, @Nullable Integer systemProcessorsMin,
                          @Nullable Integer systemProcessorsMax, @Nullable Long systemMemory,
                          @Nullable Long systemMemoryMin, @Nullable Long systemMemoryMax,
                          @Nullable String systemMemoryReadable,
                          @Nullable String systemMemoryMinReadable, @Nullable String systemMemoryMaxReadable,
                          @Nullable String osName, @Nullable String osVersion,
                          @Nullable String jvmVersion, @Nullable String jvmName,
                          @Nullable String jmhVersion) implements DataBase {

    public Environment {
        Objects.requireNonNull(name, "name must not be null");
    }

}
