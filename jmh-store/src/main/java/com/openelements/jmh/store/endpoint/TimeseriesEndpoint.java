package com.openelements.jmh.store.endpoint;

import com.openelements.jmh.store.db.repositories.BenchmarkRepository;
import com.openelements.jmh.store.db.repositories.TimeseriesRepository;
import com.openelements.jmh.store.shared.TimeseriesDefinition;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TimeseriesEndpoint {

  private final BenchmarkRepository benchmarkRepository;

  private final TimeseriesRepository timeseriesRepository;

  @Autowired
  public TimeseriesEndpoint(final BenchmarkRepository benchmarkRepository,
      final TimeseriesRepository timeseriesRepository) {
    this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository,
        "benchmarkRepository must not be null");
    this.timeseriesRepository = Objects.requireNonNull(timeseriesRepository,
        "timeseriesRepository must not be null");
  }

  @CrossOrigin
  @GetMapping("/timeseries")
  @ResponseBody
  List<TimeseriesDefinition> getTimeseries(@RequestParam Long benchmarkId) {
    return benchmarkRepository.findById(benchmarkId)
        .map(entity -> entity.getId())
        .map(id -> timeseriesRepository.findAllForBenchmark(id))
        .orElseThrow(
            () -> new IllegalArgumentException("No benchmark with id '" + benchmarkId + "' found"))
        .stream()
        .map(entity -> new TimeseriesDefinition(entity.getId(), entity.getTimestamp(),
            entity.getMeasurement(), entity.getError(), entity.getMin(), entity.getMax()))
        .sorted(Comparator.comparing(t -> t.timestamp()))
        .collect(Collectors.toList());
  }
}
