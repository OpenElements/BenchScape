package com.openelements.jmh.store.data.factory;

import com.openelements.jmh.store.data.*;
import org.openjdk.jmh.results.BenchmarkResult;
import org.openjdk.jmh.results.RunResult;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BenchmarkFactory {

    public static Benchmark convert(final RunResult jmhResult) {

        final String jmhVersion = jmhResult.getParams().getJmhVersion();
        final String jvmName = jmhResult.getParams().getVmName();
        final String jvmVersion = jmhResult.getParams().getVmVersion();
        final OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();

        final long memory = Optional.of(osBean)
                .filter(bean -> bean instanceof com.sun.management.OperatingSystemMXBean)
                .map(bean -> ((com.sun.management.OperatingSystemMXBean) bean))
                .map(bean -> bean.getTotalMemorySize())
                .orElse(-1L);


        final String arch = osBean.getArch();
        final int availableProcessors = osBean.getAvailableProcessors();
        final String osVersion = osBean.getVersion();
        final String osName = osBean.getName();

        final BenchmarkInfrastructure infrastructure = new BenchmarkInfrastructure(arch, availableProcessors, memory, osName, osVersion, jvmVersion, jvmName, jmhVersion);

        final int warmupIterations = jmhResult.getParams().getWarmup().getCount();
        final int warmupBatchSize = jmhResult.getParams().getWarmup().getBatchSize();
        final long warmupTime = jmhResult.getParams().getWarmup().getTime().getTime();
        final TimeUnit warmupTimeUnit = jmhResult.getParams().getWarmup().getTime().getTimeUnit();
        final BenchmarkMeasurementConfiguration warmupConfiguration = new BenchmarkMeasurementConfiguration(warmupIterations, warmupTime, warmupTimeUnit, warmupBatchSize);

        final int measurementIterations = jmhResult.getParams().getMeasurement().getCount();
        final int measurementBatchSize = jmhResult.getParams().getMeasurement().getBatchSize();
        final long measurementTime = jmhResult.getParams().getMeasurement().getTime().getTime();
        final TimeUnit measurementTimeUnit = jmhResult.getParams().getMeasurement().getTime().getTimeUnit();
        final BenchmarkMeasurementConfiguration measurementConfiguration = new BenchmarkMeasurementConfiguration(measurementIterations, measurementTime, measurementTimeUnit, measurementBatchSize);

        final String benchmarkName = jmhResult.getParams().getBenchmark();
        final int threads = jmhResult.getParams().getThreads();
        final int forks = jmhResult.getParams().getForks();
        final String mode = jmhResult.getParams().getMode().toString();
        final TimeUnit timeunit = jmhResult.getParams().getTimeUnit();
        final long timeout = jmhResult.getParams().getTimeout().getTime();
        final TimeUnit timeoutTimeunit = jmhResult.getParams().getTimeout().getTimeUnit();
        final BenchmarkConfiguration configuration = new BenchmarkConfiguration(threads, forks, timeunit, timeout, timeoutTimeunit, measurementConfiguration, warmupConfiguration);

        final BenchmarkResult benchmarkResult = jmhResult.getBenchmarkResults().stream().findFirst().orElseThrow();
        final Instant startTimeInstant = Instant.ofEpochMilli(benchmarkResult.getMetadata().getStartTime());
        final Instant warmupTimeInstant = Instant.ofEpochMilli(benchmarkResult.getMetadata().getWarmupTime());
        final Instant measurementTimeInstant = Instant.ofEpochMilli(benchmarkResult.getMetadata().getMeasurementTime());
        final Instant stopTimeInstant = Instant.ofEpochMilli(benchmarkResult.getMetadata().getStopTime());
        final long warmupOps = benchmarkResult.getMetadata().getWarmupOps();
        final long measurementOps = benchmarkResult.getMetadata().getMeasurementOps();
        final BenchmarkExecution execution = new BenchmarkExecution(startTimeInstant, warmupTimeInstant, measurementTimeInstant, stopTimeInstant, warmupOps, measurementOps);

        final String unit = benchmarkResult.getPrimaryResult().getScoreUnit();
        final double value = benchmarkResult.getPrimaryResult().getScore();
        final double error = benchmarkResult.getPrimaryResult().getScoreError();
        final Result result = new Result(value, error, unit);

        final Benchmark benchmark = new Benchmark(UUID.randomUUID().toString(), benchmarkName, BenchmarkType.THROUGHPUT, infrastructure, configuration, execution, result);

        return benchmark;
    }


}
