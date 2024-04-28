package com.openelements.benchscape.server.store.data;

import edu.umd.cs.findbugs.annotations.Nullable;
import java.io.Serializable;

/**
 * Data object of an environment query. This object is used to hold all parameters of a query to find matching
 * environments. All parameters are nullable. If a parameter is null it will not be used for the query. All String
 * params should support wildcards. For example: "name=Test*" will find all environments with a name starting with
 * "Test".
 *
 * @param name                name of the environment
 * @param gitOriginUrl        git origin url of the environment
 * @param gitBranch           git branch of the environment
 * @param systemArch          system architecture of the environment
 * @param systemProcessors    system processors of the environment
 * @param systemProcessorsMin min system processors of the environment
 * @param systemProcessorsMax max system processors of the environment
 * @param systemMemory        system memory of the environment
 * @param systemMemoryMin     min system memory of the environment
 * @param systemMemoryMax     max system memory of the environment
 * @param osName              os name of the environment
 * @param osVersion           os version of the environment
 * @param jvmVersion          jvm version of the environment
 * @param jvmName             jvm name of the environment
 * @param jmhVersion          jmh version of the environment
 */
public record EnvironmentQuery(@Nullable String name,
                               @Nullable String gitOriginUrl,
                               @Nullable String gitBranch,
                               @Nullable String systemArch,
                               @Nullable Integer systemProcessors,
                               @Nullable Integer systemProcessorsMin,
                               @Nullable Integer systemProcessorsMax,
                               @Nullable SystemMemory systemMemory,
                               @Nullable SystemMemory systemMemoryMin,
                               @Nullable SystemMemory systemMemoryMax,
                               @Nullable String osName,
                               @Nullable String osVersion,
                               @Nullable String jvmVersion,
                               @Nullable String jvmName,
                               @Nullable OperationSystem osFamily,
                               @Nullable String jmhVersion) implements Serializable {

}
