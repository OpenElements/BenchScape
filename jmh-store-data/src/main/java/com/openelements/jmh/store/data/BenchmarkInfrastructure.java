package com.openelements.jmh.store.data;

public record BenchmarkInfrastructure(String arch, int availableProcessors, long memory, String osName,
                                      String osVersion,
                                      String jvmVersion, String jvmName, String jmhVersion) {

}
