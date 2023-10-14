package com.openelements.jmh.store.v2.data;

import java.util.Map;
import java.util.UUID;

public record Benchmark(UUID id, String name, Map<String, String> params) {
}
