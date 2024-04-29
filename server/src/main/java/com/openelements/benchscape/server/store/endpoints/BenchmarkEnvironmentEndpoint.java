package com.openelements.benchscape.server.store.endpoints;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.ALL;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.COUNT;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.FIND;
import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.data.EnvironmentQuery;
import com.openelements.benchscape.server.store.data.OperationSystem;
import com.openelements.benchscape.server.store.data.SystemMemory;
import com.openelements.benchscape.server.store.services.EnvironmentService;
import edu.umd.cs.findbugs.annotations.NonNull;
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

/**
 * Endpoint for all REST requests regarding environments.
 */
@CrossOrigin
@RestController
@RequestMapping(V2 + "/environment")
public class BenchmarkEnvironmentEndpoint {

    private final EnvironmentService environmentService;

    /**
     * Constructor.
     *
     * @param environmentService the service to access environments
     */
    @Autowired
    public BenchmarkEnvironmentEndpoint(@NonNull final EnvironmentService environmentService) {
        this.environmentService = Objects.requireNonNull(environmentService, "environmentService must not be null");
    }

    /**
     * Get all available environments (for the current tenant - see #{@link EnvironmentService}).
     *
     * @return all available environments
     */
    @Operation(summary = "Get all available environments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "request handled without error",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Environment.class))})})
    @GetMapping(ALL)
    public List<Environment> getAll() {
        return environmentService.getAll();
    }

    /**
     * Get the environment with the given id (for the current tenant - see #{@link EnvironmentService}).
     *
     * @param id the id of the environment to get
     * @return the environment with the given id
     */
    @GetMapping(FIND)
    public Environment find(@RequestParam final String id) {
        return environmentService.find(id);
    }

    /**
     * Find environments by the given query parameters (for the current tenant - see #{@link EnvironmentService}).
     *
     * @param name                the name of the environment (can contain wildcards)
     * @param gitOriginUrl        the git origin url of the environment (can contain wildcards)
     * @param gitBranch           the git branch of the environment (can contain wildcards)
     * @param systemArch          the system architecture of the environment (can contain wildcards)
     * @param systemProcessors    the number of system processors of the environment
     * @param systemProcessorsMin the minimum number of system processors of the environment (if defined
     *                            systemProcessors will be ignored)
     * @param systemProcessorsMax the maximum number of system processors of the environment (if defined
     *                            systemProcessors will be ignored)
     * @param systemMemory        the system memory of the environment
     * @param systemMemoryMin     the minimum system memory of the environment (if defined systemMemory will be
     *                            ignored)
     * @param systemMemoryMax     the maximum system memory of the environment (if defined systemMemory will be
     *                            ignored)
     * @param osName              the os name of the environment (can contain wildcards)
     * @param osVersion           the os version of the environment (can contain wildcards)
     * @param jvmVersion          the jvm version of the environment (can contain wildcards)
     * @param jvmName             the jvm name of the environment (can contain wildcards)
     * @param jmhVersion          the jmh version of the environment (can contain wildcards)
     * @return all environments that match the given query parameters
     */
    @GetMapping("/findByQuery")
    public List<Environment> findByQuery(
            @RequestParam(required = false) String name,
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
        final EnvironmentQuery environmentQuery = new EnvironmentQuery(name, gitOriginUrl, gitBranch, systemArch,
                systemProcessors, systemProcessorsMin, systemProcessorsMax, systemMemory, systemMemoryMin,
                systemMemoryMax, osFamily, osName, osVersion, jvmVersion, jvmName, jmhVersion);
        return environmentService.findByQuery(environmentQuery);
    }


    /**
     * Get the count of all available environments (for the current tenant - see #{@link EnvironmentService}).
     *
     * @return the count of all available environments
     */
    @GetMapping(COUNT)
    public long getCount() {
        return environmentService.getCount();
    }

    /**
     * Save the given environment (for the current tenant - see #{@link EnvironmentService}).
     *
     * @param environment the environment to save
     * @return the saved environment
     */
    @PostMapping
    public Environment save(@RequestBody final Environment environment) {
        return environmentService.save(environment);
    }

    /**
     * Delete the environment with the given id (for the current tenant - see #{@link EnvironmentService}).
     *
     * @param id the id of the environment to delete
     */
    @DeleteMapping
    public void delete(@RequestParam final String id) {
        environmentService.delete(id);
    }
}
