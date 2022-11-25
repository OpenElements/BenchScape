package com.openelements.jmh.store.db;

import com.openelements.jmh.store.db.entities.BenchmarkEntity;
import com.openelements.jmh.store.db.entities.TimeseriesEntity;
import com.openelements.jmh.store.db.repositories.BenchmarkRepository;
import com.openelements.jmh.store.db.repositories.TimeseriesRepository;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;
import javax.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Transactional
@Component
public class TestDataProvider implements CommandLineRunner {

  private final BenchmarkRepository benchmarkRepository;

  private final TimeseriesRepository timeseriesRepository;

  public TestDataProvider(
      BenchmarkRepository benchmarkRepository,
      TimeseriesRepository timeseriesRepository) {
    this.benchmarkRepository = benchmarkRepository;
    this.timeseriesRepository = timeseriesRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    final long benchmark1Id = createBenchmark("Test for Foo.bar()");
    IntStream.range(0, 1_000).forEach(i -> createTimeseries(benchmark1Id));

    final long benchmark2Id = createBenchmark("Runtime test");
    IntStream.range(0, 100).forEach(i -> createTimeseries(benchmark2Id));

    final long benchmark3Id = createBenchmark("Integration test");
    IntStream.range(0, 25).forEach(i -> createTimeseries(benchmark3Id));
  }

  private void createTimeseries(final long benchmarkId) {
    final TimeseriesEntity timeseriesEntity = new TimeseriesEntity();
    timeseriesEntity.setBenchmarkId(benchmarkId);
    final Random random = new Random(System.currentTimeMillis());
    final List<TimeseriesEntity> allForBenchmark = timeseriesRepository.findAllForBenchmark(
        benchmarkId);
    if (allForBenchmark.isEmpty()) {
      timeseriesEntity.setTimestamp(ZonedDateTime.now().minusDays(random.nextInt(12)).toInstant());
      timeseriesEntity.setMeasurement(random.nextDouble(240));
    } else {
      final TimeseriesEntity lastEntity = allForBenchmark.get(allForBenchmark.size() - 1);
      final Instant newTime = lastEntity.getTimestamp().atZone(ZoneId.systemDefault())
          .plusMinutes(random.nextLong(200)).toInstant();
      final double newValue = lastEntity.getMeasurement() + (random.nextDouble(-0.1, 0.11)
          * lastEntity.getMeasurement());
      timeseriesEntity.setTimestamp(newTime);
      timeseriesEntity.setMeasurement(newValue);
    }
    timeseriesEntity.setError(random.nextDouble(12));
    timeseriesEntity.setMin(timeseriesEntity.getMeasurement() - random.nextDouble(
        timeseriesEntity.getMeasurement() / 10.0D));
    timeseriesEntity.setMax(timeseriesEntity.getMeasurement() + random.nextDouble(
        timeseriesEntity.getMeasurement() / 10.0D));
    timeseriesRepository.save(timeseriesEntity);
  }

  private long createBenchmark(final String name) {
    BenchmarkEntity benchmarkEntity = new BenchmarkEntity();
    benchmarkEntity.setName(name);
    benchmarkEntity.setUnit("ops/ms");
    return benchmarkRepository.save(benchmarkEntity).getId();
  }
}