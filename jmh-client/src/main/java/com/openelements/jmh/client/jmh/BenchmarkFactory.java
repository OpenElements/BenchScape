package com.openelements.jmh.client.jmh;

import com.openelements.jmh.common.BenchmarkConfiguration;
import com.openelements.jmh.common.BenchmarkExecution;
import com.openelements.jmh.common.BenchmarkExecutionMetadata;
import com.openelements.jmh.common.BenchmarkExecutionResult;
import com.openelements.jmh.common.BenchmarkInfrastructure;
import com.openelements.jmh.common.BenchmarkMeasurementConfiguration;
import com.openelements.jmh.common.BenchmarkType;
import com.openelements.jmh.common.BenchmarkUnit;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.time.Instant;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.infra.BenchmarkParams;
import org.openjdk.jmh.infra.IterationParams;
import org.openjdk.jmh.results.RunResult;

public class BenchmarkFactory {

    private BenchmarkFactory() {
    }

    public static Collection<BenchmarkExecution> convert(@NonNull final Collection<RunResult> jmhResults) {
        Objects.requireNonNull(jmhResults, "jmhResults must not be null");
        return jmhResults.stream().map(BenchmarkFactory::convert).toList();
    }

    @NonNull
    public static BenchmarkExecution convert(@NonNull final RunResult jmhResult) {
        Objects.requireNonNull(jmhResult, "jmhResult must not be null");
        final BenchmarkInfrastructure infrastructure = convertToBenchmarkInfrastructure(jmhResult.getParams());
        final BenchmarkConfiguration configuration = convertToBenchmarkConfiguration(jmhResult.getParams());
        final org.openjdk.jmh.results.BenchmarkResult benchmarkResult = jmhResult.getBenchmarkResults().stream()
                .findFirst().orElseThrow();
        final BenchmarkExecutionMetadata execution = convertToBenchmarkExecution(benchmarkResult);
        final BenchmarkExecutionResult result = convertToBenchmarkResult(benchmarkResult.getPrimaryResult());
        final String benchmarkName = jmhResult.getParams().getBenchmark();
        final BenchmarkExecution benchmark = new BenchmarkExecution(benchmarkName, BenchmarkType.THROUGHPUT,
                infrastructure, configuration, execution, result);
        return benchmark;
    }

    @NonNull
    private static BenchmarkInfrastructure convertToBenchmarkInfrastructure(@NonNull final BenchmarkParams params) {
        Objects.requireNonNull(params, "params must not be null");
        final String jmhVersion = params.getJmhVersion();
        final String jvmName = params.getVmName();
        final String jvmVersion = params.getVmVersion();
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
        return new BenchmarkInfrastructure(arch, availableProcessors, memory, osName, osVersion, jvmVersion, jvmName,
                jmhVersion);
    }

    @NonNull
    private static BenchmarkMeasurementConfiguration convertToBenchmarkMeasurementConfiguration(
            @NonNull final IterationParams params) {
        Objects.requireNonNull(params, "params must not be null");
        final int warmupIterations = params.getCount();
        final int warmupBatchSize = params.getBatchSize();
        final long warmupTime = params.getTime().getTime();
        final TimeUnit warmupTimeUnit = params.getTime().getTimeUnit();
        return new BenchmarkMeasurementConfiguration(warmupIterations, warmupTime, warmupTimeUnit, warmupBatchSize);
    }

    @NonNull
    private static BenchmarkConfiguration convertToBenchmarkConfiguration(@NonNull final BenchmarkParams params) {
        Objects.requireNonNull(params, "params must not be null");
        final BenchmarkMeasurementConfiguration warmupConfiguration = convertToBenchmarkMeasurementConfiguration(
                params.getWarmup());
        final BenchmarkMeasurementConfiguration measurementConfiguration = convertToBenchmarkMeasurementConfiguration(
                params.getMeasurement());
        final int threads = params.getThreads();
        final int forks = params.getForks();
        final long timeout = params.getTimeout().getTime();
        final TimeUnit timeoutTimeunit = params.getTimeout().getTimeUnit();
        return new BenchmarkConfiguration(threads, forks, timeout, timeoutTimeunit, measurementConfiguration,
                warmupConfiguration);
    }

    @NonNull
    private static BenchmarkExecutionResult convertToBenchmarkResult(
            @NonNull final org.openjdk.jmh.results.Result benchmarkResult) {
        Objects.requireNonNull(benchmarkResult, "benchmarkResult must not be null");
        final String unit = benchmarkResult.getScoreUnit();
        final double value = benchmarkResult.getScore();
        final Double error;
        if (Objects.equals(benchmarkResult.getScoreError(), Double.NaN)) {
            error = null;
        } else {
            error = benchmarkResult.getScoreError();
        }
        final Double min;
        if (Objects.equals(benchmarkResult.getStatistics().getMin(), Double.NaN)) {
            min = null;
        } else {
            min = benchmarkResult.getStatistics().getMin();
        }
        final Double max;
        if (Objects.equals(benchmarkResult.getStatistics().getMax(), Double.NaN)) {
            max = null;
        } else {
            max = benchmarkResult.getStatistics().getMax();
        }
        return new BenchmarkExecutionResult(value, error, min, max, BenchmarkUnit.getForJmhName(unit));
    }

    @NonNull
    private static BenchmarkExecutionMetadata convertToBenchmarkExecution(
            @NonNull final org.openjdk.jmh.results.BenchmarkResult benchmarkResult) {
        Objects.requireNonNull(benchmarkResult, "benchmarkResult must not be null");
        final Instant startTimeInstant = Instant.ofEpochMilli(benchmarkResult.getMetadata().getStartTime());
        final Instant warmupTimeInstant = Instant.ofEpochMilli(benchmarkResult.getMetadata().getWarmupTime());
        final Instant measurementTimeInstant = Instant.ofEpochMilli(benchmarkResult.getMetadata().getMeasurementTime());
        final Instant stopTimeInstant = Instant.ofEpochMilli(benchmarkResult.getMetadata().getStopTime());
        final long warmupOps = benchmarkResult.getMetadata().getWarmupOps();
        final long measurementOps = benchmarkResult.getMetadata().getMeasurementOps();
        return new BenchmarkExecutionMetadata(startTimeInstant, warmupTimeInstant, measurementTimeInstant,
                stopTimeInstant, warmupOps, measurementOps);
    }

}
