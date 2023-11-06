package com.openelements.jmh.store.store.endpoints;

import com.openelements.jmh.store.store.data.Benchmark;
import com.openelements.jmh.store.store.services.BenchmarkService;
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
@RequestMapping("/api/v2/benchmark")
public class BenchmarkEndpoint {

    private final BenchmarkService benchmarkService;

    @Autowired
    public BenchmarkEndpoint(@NonNull final BenchmarkService benchmarkService) {
        this.benchmarkService = Objects.requireNonNull(benchmarkService, "benchmarkService must not be null");
    }

    @GetMapping("/all")
    public List<Benchmark> getAll() {
        return benchmarkService.getAll();
    }

}
