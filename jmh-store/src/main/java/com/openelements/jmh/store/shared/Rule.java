package com.openelements.jmh.store.shared;

public record Rule(Long id, Long benchmarkId, Double maxAllowedValue, Double minAllowedValue,
                   Double maxAllowedError, Double maxAllowedErrorDeviation,
                   Double maxAllowedValueDeviation) {

}
