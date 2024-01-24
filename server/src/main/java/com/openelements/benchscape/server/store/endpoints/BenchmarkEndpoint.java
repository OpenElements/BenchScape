package com.openelements.benchscape.server.store.endpoints;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.ALL;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.COUNT;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.FIND;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.server.store.data.Benchmark;
import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.data.MeasurementMetadata;
import com.openelements.benchscape.server.store.services.BenchmarkService;
import com.openelements.benchscape.server.store.services.EnvironmentService;
import com.openelements.benchscape.server.store.services.MeasurementService;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(V2 + "/benchmark")
public class BenchmarkEndpoint {

    private final BenchmarkService benchmarkService;

    private final MeasurementService measurementService;

    private final EnvironmentService environmentService;

    @Autowired
    public BenchmarkEndpoint(@NonNull final BenchmarkService benchmarkService,
            @NonNull final MeasurementService measurementService,
            @NonNull final EnvironmentService environmentService) {
        this.benchmarkService = Objects.requireNonNull(benchmarkService, "benchmarkService must not be null");
        this.measurementService = Objects.requireNonNull(measurementService, "measurementService must not be null");
        this.environmentService = Objects.requireNonNull(environmentService, "environmentService must not be null");
    }

    @GetMapping(ALL)
    public List<Benchmark> getAll() {
        return benchmarkService.getAll();
    }

    @GetMapping(COUNT)
    public long getCount() {
        return benchmarkService.getCount();
    }

    @GetMapping(FIND)
    public Benchmark find(@RequestParam final String id) {
        return benchmarkService.find(id);
    }

    @GetMapping("/findMatchingEnvironments")
    public List<Environment> findEnvironmentsForBenchmark(@RequestParam final String benchmarkId) {
        final List<MeasurementMetadata> metadata = measurementService.findAllForBenchmark(benchmarkId)
                .stream()
                .map(m -> measurementService.getMetadataForMeasurement(m.id()))
                .toList();
        return environmentService.getAll().stream()
                .filter(e -> matchesAnyMetadata(e, metadata))
                .toList();
    }

    private boolean matchesAnyMetadata(Environment e, List<MeasurementMetadata> metadata) {
        return metadata.stream()
                .filter(m -> environmentService.isMatchingEnvironment(m, e))
                .findAny()
                .isPresent();
    }
}
