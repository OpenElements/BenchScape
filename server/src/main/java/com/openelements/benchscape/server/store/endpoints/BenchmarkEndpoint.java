package com.openelements.benchscape.server.store.endpoints;

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

/**
 * Endpoint for all REST requests regarding benchmarks.
 */
@CrossOrigin
@RestController
@RequestMapping(V2 + "/benchmark")
public class BenchmarkEndpoint {

    private final BenchmarkService benchmarkService;

    private final MeasurementService measurementService;

    private final EnvironmentService environmentService;

    /**
     * Constructor.
     *
     * @param benchmarkService   the service to access benchmarks
     * @param measurementService the service to access measurements
     * @param environmentService the service to access environments
     */
    @Autowired
    public BenchmarkEndpoint(@NonNull final BenchmarkService benchmarkService,
            @NonNull final MeasurementService measurementService,
            @NonNull final EnvironmentService environmentService) {
        this.benchmarkService = Objects.requireNonNull(benchmarkService, "benchmarkService must not be null");
        this.measurementService = Objects.requireNonNull(measurementService, "measurementService must not be null");
        this.environmentService = Objects.requireNonNull(environmentService, "environmentService must not be null");
    }

    /**
     * Get all available benchmarks (for the current tenant - see #{@link BenchmarkService}).
     *
     * @return all available benchmarks
     */
    @GetMapping("/all1")
    @RequestMapping(value = "/all2", method = {org.springframework.web.bind.annotation.RequestMethod.GET})
    public List<Benchmark> getAll() {
        return benchmarkService.getAll();
    }

    /**
     * Get the count of all available benchmarks (for the current tenant - see #{@link BenchmarkService}).
     *
     * @return the count of all available benchmarks
     */
    @GetMapping(COUNT)
    public long getCount() {
        return benchmarkService.getCount();
    }

    /**
     * Find a benchmark by its id (for the current tenant - see #{@link BenchmarkService}).
     *
     * @param id the id of the benchmark
     * @return the benchmark
     */
    @GetMapping(FIND)
    public Benchmark find(@RequestParam final String id) {
        return benchmarkService.find(id);
    }

    /**
     * Find all environments that match the metadata of the measurements of the benchmark with the given id (for the
     * current tenant - see #{@link EnvironmentService}).
     *
     * @param benchmarkId the id of the benchmark
     * @return all environments that match the metadata of the measurements of the benchmark with the given id
     */
    @GetMapping("/findMatchingEnvironments")
    public List<Environment> findEnvironmentsForBenchmark(@RequestParam final String benchmarkId) {
        final List<MeasurementMetadata> metadata = measurementService.findAllForBenchmark(benchmarkId)
                .stream()
                .map(m -> measurementService.getMetadataForMeasurement(m.id()))
                .toList();
        return environmentService.getAll().stream()
                .filter(e -> environmentService.isAnyMatchingEnvironment(e, metadata))
                .toList();
    }
}
