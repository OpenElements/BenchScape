package com.openelements.jmh.store.v2.endpoints;

import com.openelements.jmh.store.v2.data.Environment;
import com.openelements.jmh.store.v2.services.EnvironmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/api/v2/environment")
public class BenchmarkEnvironmentEndpoint {

    private final EnvironmentService environmentService;


    @Autowired
    public BenchmarkEnvironmentEndpoint(EnvironmentService environmentService) {
        this.environmentService = Objects.requireNonNull(environmentService, "environmentService must not be null");
    }

    @Operation(summary = "Get all available environments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "request handled without error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Environment.class))})})
    @GetMapping("/all")
    public List<Environment> getAll() {
        return environmentService.getAll();
    }

    @GetMapping("/find")
    public Environment find(@RequestParam final String id) {
        return environmentService.find(id);
    }

    @PostMapping
    public Environment save(@RequestBody final Environment environment) {
        return environmentService.save(environment);
    }

    @DeleteMapping
    public void delete(@RequestParam final String id) {
        environmentService.delete(id);
    }
}
