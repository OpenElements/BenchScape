package com.openelements.benchscape.jmh.model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;

/**
 * Metadata about the infrastructure on which the benchmark was executed.
 *
 * @param arch                the architecture of the CPU
 * @param availableProcessors the number of available processors
 * @param memory              the amount of memory in bytes
 * @param osName              the name of the operating system
 * @param osVersion           the version of the operating system
 * @param jvmVersion          the version of the JVM
 * @param jvmName             the name of the JVM
 * @param jmhVersion          the version of JMH
 */
public record BenchmarkInfrastructure(@NotNull String arch, @Min(1) int availableProcessors, @Min(1) long memory,
                                      @NotNull String osName,
                                      @NotNull String osVersion,
                                      @NotNull String jvmVersion, @NotNull String jvmName,
                                      @NotNull Map<String, String> systemProperties,
                                      @NotNull Map<String, String> environmentProperties, @NotNull String jmhVersion) {

    public BenchmarkInfrastructure {
        Objects.requireNonNull(arch, "arch must not be null");
        Objects.requireNonNull(osName, "osName must not be null");
        if (availableProcessors < 1) {
            throw new IllegalArgumentException("availableProcessors must be greater than 0");
        }
        if (memory < 1) {
            throw new IllegalArgumentException("memory must be greater than 0");
        }
        Objects.requireNonNull(osVersion, "osVersion must not be null");
        Objects.requireNonNull(jvmVersion, "jvmVersion must not be null");
        Objects.requireNonNull(jvmName, "jvmName must not be null");
        Objects.requireNonNull(systemProperties, "systemProperties must not be null");
        Objects.requireNonNull(environmentProperties, "environmentProperties must not be null");
        Objects.requireNonNull(jmhVersion, "jmhVersion must not be null");
    }
}
