package com.openelements.benchscape.server.store.endpoints;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.ALL;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.COUNT;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.server.store.data.Benchmark;
import com.openelements.benchscape.server.store.services.BenchmarkService;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(V2 + "/benchmark")
public class BenchmarkEndpoint {

    private final BenchmarkService benchmarkService;

    @Autowired
    public BenchmarkEndpoint(@NonNull final BenchmarkService benchmarkService) {
        this.benchmarkService = Objects.requireNonNull(benchmarkService, "benchmarkService must not be null");
    }

    @GetMapping(ALL)
    public List<Benchmark> getAll() {
        return benchmarkService.getAll();
    }

    @GetMapping(COUNT)
    public long getCount() {
        return benchmarkService.getCount();
    }
}
