package com.openelements.jmh.common;

public record BenchmarkInfrastructure(String arch, int availableProcessors, long memory, String osName,
                                      String osVersion,
                                      String jvmVersion, String jvmName, String jmhVersion) {

}
