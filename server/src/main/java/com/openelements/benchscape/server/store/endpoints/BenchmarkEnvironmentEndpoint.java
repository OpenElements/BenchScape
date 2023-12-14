package com.openelements.benchscape.server.store.endpoints;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.ALL;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.COUNT;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.FIND;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.services.EnvironmentService;
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
@RequestMapping(V2 + "/environment")
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
    @GetMapping(ALL)
    public List<Environment> getAll() {
        return environmentService.getAll();
    }

    @GetMapping(FIND)
    public Environment find(@RequestParam final String id) {
        return environmentService.find(id);
    }

    @GetMapping("/findBy") //FUTURE: We need a good and consistent naming for the endpoints
    public List<Environment> findBy(@RequestParam final String benchmarkId) {
        return List.of();
    }


    @GetMapping(COUNT)
    public long getCount() {
        return environmentService.getCount();
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
