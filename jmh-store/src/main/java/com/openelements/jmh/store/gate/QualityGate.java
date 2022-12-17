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

      if (rule.failOnDifferentProcessorCount()) {
        checkProcessorCount(timeseries, benchmark).ifPresent(violation -> result.add(violation));
      }

      if (rule.failOnDifferentMemorySize()) {
        checkMemorySize(timeseries, benchmark).ifPresent(violation -> result.add(violation));
      }

      if (rule.failOnDifferentJvmVersion()) {
        checkMemorySize(timeseries, benchmark).ifPresent(violation -> result.add(violation));
      }

    });
    return Collections.unmodifiableSet(result);
  }

  private Optional<Violation> checkProcessorCount(final Timeseries timeseries,
      final BenchmarkDefinition benchmark) {
    final Integer benchmarkProcessorCount = benchmark.availableProcessors();
    if (benchmarkProcessorCount == null) {
      return Optional.of(new Violation(Violation.BENCHMARK_PROCESSOR_COUNT_UNDEFINED_ID,
          "Processor count of benchmark not defined"));
    }
    final Integer timeseriesProcessorCount = timeseries.availableProcessors();
    if (timeseriesProcessorCount == null) {
      return Optional.of(new Violation(Violation.RESULT_PROCESSOR_COUNT_UNDEFINED_ID,
          "Processor count of result not defined"));
    }
    if (!Objects.equals(benchmarkProcessorCount, timeseriesProcessorCount)) {
      return Optional.of(new Violation(Violation.WRONG_PROCESSOR_COUNT_ID,
          "Processor count of result (" + timeseriesProcessorCount
              + ") does not fit to defined processor count of benchmark (" + benchmarkProcessorCount
              + ")"));
    }
    return Optional.empty();
  }

  private Optional<Violation> checkMemorySize(final Timeseries timeseries,
      final BenchmarkDefinition benchmark) {
    final Long benchmarkMemorySize = benchmark.memory();
    if (benchmarkMemorySize == null) {
      return Optional.of(new Violation(Violation.BENCHMARK_MEMORY_SIZE_UNDEFINED_ID,
          "Processor count of benchmark not defined"));
    }
    final Long timeseriesMemorySize = timeseries.memory();
    if (timeseriesMemorySize == null) {
      return Optional.of(new Violation(Violation.RESULT_MEMORY_SIZE_UNDEFINED_ID,
          "Processor count of result not defined"));
    }
    if (!Objects.equals(benchmarkMemorySize, timeseriesMemorySize)) {
      return Optional.of(new Violation(Violation.WRONG_MEMORY_SIZE_ID,
          "Memory size of result (" + timeseriesMemorySize
              + ") does not fit to defined memory size of benchmark (" + benchmarkMemorySize
              + ")"));
    }
    return Optional.empty();
  }

  private Optional<Violation> checkJvmVersion(final Timeseries timeseries,
      final BenchmarkDefinition benchmark) {
    final String benchmarkJvmVersion = benchmark.jvmVersion();
    if (benchmarkJvmVersion == null) {
      return Optional.of(new Violation(Violation.BENCHMARK_JVM_VERSION_UNDEFINED_ID,
          "Processor count of benchmark not defined"));
    }
    final String timeseriesJvmVersion = timeseries.jvmVersion();
    if (timeseriesJvmVersion == null) {
      return Optional.of(new Violation(Violation.RESULT_JVM_VERSION_UNDEFINED_ID,
          "Processor count of result not defined"));
    }
    if (!Objects.equals(benchmarkJvmVersion, timeseriesJvmVersion)) {
      return Optional.of(new Violation(Violation.WRONG_JVM_VERSION_ID,
          "JVM version of result (" + timeseriesJvmVersion
              + ") does not fit to defined JVM version of benchmark (" + benchmarkJvmVersion
              + ")"));
    }
    return Optional.empty();
  }

  private Optional<Violation> checkMaxAllowedValue(final Timeseries timeseries,
      final double maxAllowedValue) {
    Objects.requireNonNull(timeseries);
    if (timeseries.value() > maxAllowedValue) {
      return Optional.of(new Violation(Violation.MAX_ALLOWED_VALUE_FAILED_ID,
          "Value of benchmark > " + maxAllowedValue));
    }
    return Optional.empty();
  }

  private Optional<Violation> checkMinAllowedValue(final Timeseries timeseries,
      final double minAllowedValue) {
    Objects.requireNonNull(timeseries);
    if (timeseries.value() < minAllowedValue) {
      return Optional.of(new Violation(Violation.MIN_ALLOWED_VALUE_FAILED_ID,
          "Value of benchmark < " + minAllowedValue));
    }
    return Optional.empty();
  }

  private Optional<Violation> checkMaxAllowedError(final Timeseries timeseries,
      final double maxAllowedError) {
    Objects.requireNonNull(timeseries);
    if (timeseries.error() > maxAllowedError) {
      return Optional.of(new Violation(Violation.MAX_ALLOWED_ERROR_FAILED_ID,
          "Error of value of benchmark > " + maxAllowedError));
    }
    return Optional.empty();
  }
}
