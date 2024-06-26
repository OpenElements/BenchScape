package com.openelements.benchscape.server.store.endpoints;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.FIND;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import com.openelements.benchscape.server.store.data.DateTimePeriode;
import com.openelements.benchscape.server.store.data.Measurement;
import com.openelements.benchscape.server.store.data.MeasurementQuery;
import com.openelements.benchscape.server.store.math.InterpolatedMeasurement;
import com.openelements.benchscape.server.store.math.InterpolationType;
import com.openelements.benchscape.server.store.math.InterpolationUtils;
import com.openelements.benchscape.server.store.services.MeasurementService;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(V2 + "/measurement")
public class MeasurementEndpoint {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementEndpoint(@NonNull final MeasurementService measurementService) {
        this.measurementService = Objects.requireNonNull(measurementService, "measurementService must not be null");
    }

    @GetMapping(FIND)
    List<Measurement> find(@RequestParam final String benchmarkId,
            @RequestParam(required = false) final BenchmarkUnit unit,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime end,
            @RequestParam(required = false) final List<String> environmentIds,
            @RequestParam(required = false) final String gitOriginUrl,
            @RequestParam(required = false) final String gitBranch,
            @RequestParam(required = false, defaultValue = "false") final boolean smooth) {
        Objects.requireNonNull(benchmarkId, "benchmarkId must not be null");
        final BenchmarkUnit queryUnit = Optional.ofNullable(unit).orElse(BenchmarkUnit.OPERATIONS_PER_MILLISECOND);
        final ZonedDateTime queryStart = Optional.ofNullable(start)
                .orElse(LocalDateTime.MIN.atZone(ZoneId.systemDefault()));
        final ZonedDateTime queryEnd = Optional.ofNullable(end)
                .orElse(LocalDateTime.MAX.atZone(ZoneId.systemDefault()));
        final List<String> queryEnvironmentIds = Optional.ofNullable(environmentIds)
                .map(list -> Collections.unmodifiableList(list)).orElse(List.of());
        final MeasurementQuery query = new MeasurementQuery(benchmarkId, queryUnit,
                queryStart.toInstant(), queryEnd.toInstant(), queryEnvironmentIds, gitOriginUrl, gitBranch);
        final List<Measurement> measurements = measurementService.find(query);
        if (smooth) {
            return InterpolationUtils.smooth(measurements);
        } else {
            return measurements;
        }
    }

    @GetMapping(FIND + "Interpolated")
    List<InterpolatedMeasurement> findInterpolated(@RequestParam final String benchmarkId,
            @RequestParam(required = false) final BenchmarkUnit unit,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime end,
            @RequestParam(required = false) final List<String> environmentIds,
            @RequestParam(required = false) final InterpolationType interpolationType,
            @RequestParam(required = false) final String gitOriginUrl,
            @RequestParam(required = false) final String gitBranch,
            @RequestParam(required = false, defaultValue = "10") final int interpolationPoints) {
        Objects.requireNonNull(benchmarkId, "benchmarkId must not be null");
        final BenchmarkUnit queryUnit = Optional.ofNullable(unit).orElse(BenchmarkUnit.OPERATIONS_PER_MILLISECOND);
        final ZonedDateTime queryStart = Optional.ofNullable(start)
                .orElse(LocalDateTime.MIN.atZone(ZoneId.systemDefault()));
        final ZonedDateTime queryEnd = Optional.ofNullable(end)
                .orElse(LocalDateTime.MAX.atZone(ZoneId.systemDefault()));
        final List<String> queryEnvironmentIds = Optional.ofNullable(environmentIds)
                .map(list -> Collections.unmodifiableList(list)).orElse(List.of());
        final MeasurementQuery query = new MeasurementQuery(benchmarkId, queryUnit,
                queryStart.toInstant(), queryEnd.toInstant(), queryEnvironmentIds, gitOriginUrl, gitBranch);
        final List<Measurement> measurements = measurementService.find(query);
        return InterpolationUtils.interpolate(measurements,
                Optional.ofNullable(interpolationType).orElse(InterpolationType.NONE), interpolationPoints);
    }

    @GetMapping("/periode")
    DateTimePeriode getPeriode(@RequestParam final String benchmarkId) {
        Objects.requireNonNull(benchmarkId, "benchmarkId must not be null");
        return measurementService.getPeriode(benchmarkId);
    }

    @GetMapping("/findByQuery")
    List<Measurement> findByQuery(@RequestParam final String benchmarkId,
                                  @RequestParam(required = false) final BenchmarkUnit unit,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime start,
                                  @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime end,
                                  @RequestParam(required = false) final List<String> environmentIds,
                                  @RequestParam(required = false) final String gitOriginUrl,
                                  @RequestParam(required = false) final String gitBranch,
                                  @RequestParam(required = false, defaultValue = "false") final boolean smooth) {
        Objects.requireNonNull(benchmarkId, "benchmarkId must not be null");
        final BenchmarkUnit queryUnit = Optional.ofNullable(unit).orElse(BenchmarkUnit.OPERATIONS_PER_MILLISECOND);
        final ZonedDateTime queryStart = Optional.ofNullable(start)
                .orElse(LocalDateTime.MIN.atZone(ZoneId.systemDefault()));
        final ZonedDateTime queryEnd = Optional.ofNullable(end)
                .orElse(LocalDateTime.MAX.atZone(ZoneId.systemDefault()));
        final List<String> queryEnvironmentIds = Optional.ofNullable(environmentIds)
                .map(list -> Collections.unmodifiableList(list)).orElse(List.of());
        final MeasurementQuery query = new MeasurementQuery(benchmarkId, queryUnit,
                queryStart.toInstant(), queryEnd.toInstant(), queryEnvironmentIds, gitOriginUrl, gitBranch);
        final List<Measurement> measurements = measurementService.find(query);
        if (smooth) {
            return InterpolationUtils.smooth(measurements);
        } else {
            return measurements;
        }
    }

    @GetMapping("/{benchmarkId}/branches")
    public List<String> getGitBranchesForBenchmark(@PathVariable String benchmarkId) {
        return measurementService.getBranchesForBenchmark(benchmarkId);
    }
}

