package com.openelements.jmh.common;

import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Objects;

/**
 * Metadata about the infrastructure on which the benchmark was executed.
 * @param arch the architecture of the CPU
 * @param availableProcessors the number of available processors
 * @param memory the amount of memory in bytes
 * @param osName the name of the operating system
 * @param osVersion the version of the operating system
 * @param jvmVersion the version of the JVM
 * @param jvmName the name of the JVM
 * @param jmhVersion the version of JMH
 */
public record BenchmarkInfrastructure(@NonNull String arch, int availableProcessors, long memory, @NonNull String osName,
                                      @NonNull String osVersion,
                                      @NonNull String jvmVersion, @NonNull String jvmName, @NonNull String jmhVersion) {

    public BenchmarkInfrastructure {
        Objects.requireNonNull(arch, "arch must not be null");
        Objects.requireNonNull(osName, "osName must not be null");
        if(availableProcessors < 1) {
            throw new IllegalArgumentException("availableProcessors must be greater than 0");
        }
        if(memory < 1) {
            throw new IllegalArgumentException("memory must be greater than 0");
        }
        Objects.requireNonNull(osVersion, "osVersion must not be null");
        Objects.requireNonNull(jvmVersion, "jvmVersion must not be null");
        Objects.requireNonNull(jvmName, "jvmName must not be null");
        Objects.requireNonNull(jmhVersion, "jmhVersion must not be null");
    }
}
