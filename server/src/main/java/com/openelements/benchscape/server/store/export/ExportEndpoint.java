package com.openelements.benchscape.server.store.export;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import com.openelements.benchscape.server.store.data.*;
import com.openelements.benchscape.server.store.services.BenchmarkService;
import com.openelements.benchscape.server.store.services.EnvironmentService;
import com.openelements.benchscape.server.store.services.MeasurementService;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(V2 + "/export")
public class ExportEndpoint {

    private final EnvironmentService environmentService;

    private final BenchmarkService benchmarkService;

    private final MeasurementService measurementService;

    @Autowired
    public ExportEndpoint(@NonNull final EnvironmentService environmentService,
            @NonNull final BenchmarkService benchmarkService,
            @NonNull final MeasurementService measurementService) {
        this.environmentService = Objects.requireNonNull(environmentService, "environmentService must not be null");
        this.benchmarkService = Objects.requireNonNull(benchmarkService, "benchmarkService must not be null");
        this.measurementService = Objects.requireNonNull(measurementService, "measurementService must not be null");
    }

    @GetMapping(value = "/environments/csv", produces = "text/csv")
    public @ResponseBody byte[] getCsvExportOfEnvironments(@RequestParam(required = false) String name,
                                                           @RequestParam(required = false) String gitOriginUrl,
                                                           @RequestParam(required = false) String gitBranch,
                                                           @RequestParam(required = false) String systemArch,
                                                           @RequestParam(required = false) Integer systemProcessors,
                                                           @RequestParam(required = false) Integer systemProcessorsMin,
                                                           @RequestParam(required = false) Integer systemProcessorsMax,
                                                           @RequestParam(required = false) SystemMemory systemMemory,
                                                           @RequestParam(required = false) SystemMemory systemMemoryMin,
                                                           @RequestParam(required = false) SystemMemory systemMemoryMax,
                                                           @RequestParam(required = false) OperationSystem osFamily,
                                                           @RequestParam(required = false) String osName,
                                                           @RequestParam(required = false) String osVersion,
                                                           @RequestParam(required = false) String jvmVersion,
                                                           @RequestParam(required = false) String jvmName,
                                                           @RequestParam(required = false) String jmhVersion) {
        final List<Environment> filteredEnvironments = environmentService.getFilteredEnvironments(name, gitOriginUrl, gitBranch,
                systemArch, systemProcessors, systemProcessorsMin, systemProcessorsMax, systemMemory, systemMemoryMin, systemMemoryMax,
                osFamily, osName, osVersion, jvmVersion, jvmName, jmhVersion);
        return createData(osw -> CsvExport.exportEnvironments(osw, filteredEnvironments));
    }

    @GetMapping(value = "/benchmarks/csv", produces = "text/csv")
    public @ResponseBody byte[] getCsvExportOfBenchmarks() {
        final List<Benchmark> all = benchmarkService.getAll();
        return createData(osw -> CsvExport.exportBenchmarks(osw, all));
    }

    @GetMapping(value = "/measurements/csv", produces = "text/csv")
    public @ResponseBody byte[] getCsvExportOfMeasurements(@RequestParam final String benchmarkId,
            @RequestParam(required = false) final BenchmarkUnit unit,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final ZonedDateTime end,
            @RequestParam(required = false) final String gitOriginUrl,
            @RequestParam(required = false) final String gitBranch,
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
                queryStart.toInstant(), queryEnd.toInstant(), queryEnvironmentIds, gitOriginUrl, gitBranch);
        final List<Measurement> measurements = measurementService.find(query);
        return createData(osw -> CsvExport.exportMeasurements(osw, measurements));
    }

    private byte[] createData(@NonNull final Consumer<Appendable> consumer) {
        Objects.requireNonNull(consumer, "consumer must not be null");
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            final OutputStreamWriter osw = new OutputStreamWriter(baos);
            consumer.accept(osw);
            return baos.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error writing bytes", e);
        }
    }
}
