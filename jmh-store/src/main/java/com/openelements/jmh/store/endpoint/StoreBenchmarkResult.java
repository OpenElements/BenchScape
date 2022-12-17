package com.openelements.jmh.store.endpoint;

import com.openelements.jmh.store.shared.Violation;
import java.util.Set;

public record StoreBenchmarkResult(Long benchmarkId, Long timeseriesId,
                                   Set<Violation> checkResult) {

}
