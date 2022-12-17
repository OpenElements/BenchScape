package com.openelements.jmh.store.shared;

public record BenchmarkDefinition(Long id, String name, String unit, Integer availableProcessors,
                                  Long memory, String jvmVersion) {

}
