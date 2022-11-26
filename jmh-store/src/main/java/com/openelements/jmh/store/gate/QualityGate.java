package com.openelements.jmh.store.gate;

import com.openelements.jmh.store.db.DataService;
import com.openelements.jmh.store.shared.BenchmarkDefinition;
import com.openelements.jmh.store.shared.Timeseries;
import com.openelements.jmh.store.shared.Violation;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QualityGate {

  private final DataService dataService;

  @Autowired
  public QualityGate(final DataService dataService) {
    this.dataService = Objects.requireNonNull(dataService);
  }

  public Set<Violation> check(final BenchmarkDefinition benchmark,
      final Timeseries timeseries) {
    Objects.requireNonNull(benchmark);
    Objects.requireNonNull(timeseries);

    final Set<Violation> result = new HashSet<>();

    final Long benchmarkId = benchmark.id();
    dataService.getRuleForBenchmark(benchmarkId).ifPresent(rule -> {
      Optional.ofNullable(rule.maxAllowedValue())
          .map(maxAllowedValue -> checkMaxAllowedValue(timeseries, maxAllowedValue))
          .map(optionalViolation -> optionalViolation.orElse(null))
          .ifPresent(violation -> result.add(violation));

      Optional.ofNullable(rule.minAllowedValue())
          .map(minAllowedValue -> checkMinAllowedValue(timeseries, minAllowedValue))
          .map(optionalViolation -> optionalViolation.orElse(null))
          .ifPresent(violation -> result.add(violation));

      Optional.ofNullable(rule.maxAllowedError())
          .map(maxAllowedError -> checkMaxAllowedError(timeseries, maxAllowedError))
          .map(optionalViolation -> optionalViolation.orElse(null))
          .ifPresent(violation -> result.add(violation));
    });
    return Collections.unmodifiableSet(result);
  }


  private Optional<Violation> checkMaxAllowedValue(final Timeseries timeseries,
      final double maxAllowedValue) {
    Objects.requireNonNull(timeseries);
    if (timeseries.value() > maxAllowedValue) {
      return Optional.of(new Violation("Value of benchmark > " + maxAllowedValue));
    }
    return Optional.empty();
  }

  private Optional<Violation> checkMinAllowedValue(final Timeseries timeseries,
      final double minAllowedValue) {
    Objects.requireNonNull(timeseries);
    if (timeseries.value() < minAllowedValue) {
      return Optional.of(new Violation("Value of benchmark < " + minAllowedValue));
    }
    return Optional.empty();
  }

  private Optional<Violation> checkMaxAllowedError(final Timeseries timeseries,
      final double maxAllowedError) {
    Objects.requireNonNull(timeseries);
    if (timeseries.error() > maxAllowedError) {
      return Optional.of(new Violation("Error of value of benchmark > " + maxAllowedError));
    }
    return Optional.empty();
  }
}
