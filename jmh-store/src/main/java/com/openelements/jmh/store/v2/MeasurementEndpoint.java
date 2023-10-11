package com.openelements.jmh.store.v2;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import com.openelements.jmh.store.v2.data.Measurement;
import com.openelements.jmh.store.v2.data.MeasurementQuery;
import com.openelements.jmh.store.v2.services.MeasurementService;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/measurement")
public class MeasurementEndpoint {

    private final MeasurementService measurementService;

    @Autowired
    public MeasurementEndpoint(@NonNull final MeasurementService measurementService) {
        this.measurementService = Objects.requireNonNull(measurementService, "measurementService must not be null");
    }

    @GetMapping("/find")
    List<Measurement> find(@RequestParam final String benchmarkId,
            @RequestParam(required = false) final BenchmarkUnit unit,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime end,
            @RequestParam(required = false) final List<String> environmentIds) {
        Objects.requireNonNull(benchmarkId, "benchmarkId must not be null");
        final BenchmarkUnit queryUnit = Optional.ofNullable(unit).orElse(BenchmarkUnit.OPERATIONS_PER_MILLISECOND);
        final ZonedDateTime queryStart = Optional.ofNullable(start)
                .orElse(LocalDateTime.MIN.atZone(ZoneId.systemDefault()));
        final ZonedDateTime queryEnd = Optional.ofNullable(end)
                .orElse(LocalDateTime.MAX.atZone(ZoneId.systemDefault()));
        final List<String> queryEnvironmentIds = Optional.ofNullable(environmentIds)
                .map(list -> Collections.unmodifiableList(list)).orElse(List.of());
        final MeasurementQuery query = new MeasurementQuery(benchmarkId, queryUnit,
                queryStart.toInstant(), queryEnd.toInstant(), queryEnvironmentIds);
        return measurementService.find(query);
    }

}
