package com.openelements.jmh.store.v2;


import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import com.openelements.jmh.store.v2.services.BenchmarkExecutionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.Objects;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/execution")
public class BenchmarkExecutionEndpoint {

    private final BenchmarkExecutionService benchmarkExecutionService;

    public BenchmarkExecutionEndpoint(final BenchmarkExecutionService benchmarkExecutionService) {
        this.benchmarkExecutionService = Objects.requireNonNull(benchmarkExecutionService,
                "benchmarkExecutionService must not be null");
    }

    @Operation(summary = "Store a given execution")
    @PostMapping
    public void add(
            @Parameter(description = "execution that should be stored") @RequestParam final BenchmarkExecution benchmarkExecution) {
        benchmarkExecutionService.add(benchmarkExecution);
    }
}
