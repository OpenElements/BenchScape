package com.openelements.benchscape.server.store.endpoints;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.data.OperationSystem;
import com.openelements.benchscape.server.store.data.SystemMemory;
import com.openelements.benchscape.server.store.services.EnvironmentService;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.List;
import java.util.Objects;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Endpoint for all REST requests regarding environment properties. This has been externalized from
 * {@link BenchmarkEnvironmentEndpoint} to reduce the size of the class.
 */
@CrossOrigin
@RestController
@RequestMapping(V2 + "/environment/metadata")
public class BenchmarkEnvironmentMetadataEndpoint {

    private final EnvironmentService environmentService;

    /**
     * Constructor.
     *
     * @param environmentService the service to access environments
     */
    public BenchmarkEnvironmentMetadataEndpoint(@NonNull final EnvironmentService environmentService) {
        this.environmentService = Objects.requireNonNull(environmentService, "environmentService must not be null");
    }

    /**
     * Get a list of all operating systems that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all operating systems
     */
    @GetMapping("/os")
    public List<String> getAllOperationSystems() {
        return environmentService.getAll().stream()
                .map(Environment::osName)
                .filter(osName -> osName != null && !osName.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all operating systems that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all operating systems
     */
    @GetMapping("/osFamily")
    public List<OperationSystem> getAllOperationSystemFamilies() {
        return environmentService.getAll().stream()
                .map(Environment::osFamily)
                .filter(osFamily -> osFamily != null)
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all operating system versions that are currently part of environments (for the current tenant - see
     * * #{@link EnvironmentService}).
     *
     * @return all operating system versions
     */
    @GetMapping("/osVersion")
    public List<String> getAllOperationSystemsVersions() {
        return environmentService.getAll().stream()
                .map(Environment::osVersion)
                .filter(osVersion -> osVersion != null && !osVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all operating system versions for a specific os that are currently part of environments (for the
     * current tenant - see * #{@link EnvironmentService}).
     *
     * @param osName the os name to get the versions for
     * @return all operating system versions
     */
    @GetMapping("/osVersion/forOs")
    public List<String> getAllOperationSystemsVersionsByOsName(@RequestParam final String osName) {
        return environmentService.getAll().stream()
                .filter(environment -> Objects.equals(environment.osName(), osName))
                .map(Environment::osVersion)
                .filter(osVersion -> osVersion != null && !osVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all operating system versions for a specific os that are currently part of environments (for the
     * current tenant - see * #{@link EnvironmentService}).
     *
     * @param osFamily the os family to get the versions for
     * @return all operating system versions
     */
    @GetMapping("/osVersion/forOsFamily")
    public List<String> getAllOperationSystemsVersionsByOsFamily(@RequestParam final OperationSystem osFamily) {
        return environmentService.getAll().stream()
                .filter(environment -> Objects.equals(environment.osFamily(), osFamily))
                .map(Environment::osVersion)
                .filter(osVersion -> osVersion != null && !osVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all architectures that are currently part of environments (for the current tenant - see *
     * #{@link EnvironmentService}).
     *
     * @return all architectures
     */
    @GetMapping("/arch")
    public List<String> getAllArchitectures() {
        return environmentService.getAll().stream()
                .map(Environment::systemArch)
                .filter(systemArch -> systemArch != null && !systemArch.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all cpu core counts that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all cpu core counts
     */
    @GetMapping("/cores")
    public List<Integer> getAllCpuCores() {
        return environmentService.getAll().stream()
                .map(Environment::systemProcessors)
                .filter(cores -> cores != null)
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all memory variants that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all memory variants
     */
    @GetMapping("/memory")
    public List<Long> getAllMemory() {
        return environmentService.getAll().stream()
                .map(Environment::systemMemory)
                .filter(memory -> memory != null)
                .map(memory -> memory.toBytes())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all jvm names that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all jvm names
     */
    @GetMapping("/jvmName")
    public List<String> getAllJvmNames() {
        return environmentService.getAll().stream()
                .map(Environment::jvmName)
                .filter(jvmName -> jvmName != null && !jvmName.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all jvm versions that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all jvm versions
     */
    @GetMapping("/jvmVersion")
    public List<String> getAllJvmVersions() {
        return environmentService.getAll().stream()
                .map(Environment::jvmVersion)
                .filter(jvmVersion -> jvmVersion != null && !jvmVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all jvm versions for a specific jvm name that are currently part of environments (for the current
     * tenant - see #{@link EnvironmentService}).
     *
     * @param jvmName the jvm name to get the versions for
     * @return all jvm versions
     */
    @GetMapping("/jvmVersion/forJvmName")
    public List<String> getAllJvmVersions(@RequestParam final String jvmName) {
        return environmentService.getAll().stream()
                .filter(environment -> Objects.equals(environment.jvmName(), jvmName))
                .map(Environment::jvmVersion)
                .filter(jvmVersion -> jvmVersion != null && !jvmVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all jvm versions for a specific jvm version that are currently part of environments (for the current
     * tenant - see #{@link EnvironmentService}).
     *
     * @param jvmVersion the jvm version to get the names for
     * @return all jvm names
     */
    @GetMapping("/jvmVersion/forJvmVersion")
    public List<String> getAllJvmNamesForJvmVersion(@RequestParam final String jvmVersion) {
        return environmentService.getAll().stream()
                .filter(environment -> Objects.equals(environment.jvmVersion(), jvmVersion))
                .map(Environment::jvmName)
                .filter(jvmName -> jvmName != null && !jvmName.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all jmh versions that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all jmh versions
     */
    @GetMapping("/jmhVersion")
    public List<String> getAllJvhVersions() {
        return environmentService.getAll().stream()
                .map(Environment::jmhVersion)
                .filter(jmhVersion -> jmhVersion != null && !jmhVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all jvm names for a specific jmh version that are currently part of environments (for the current
     * tenant - see #{@link EnvironmentService}).
     *
     * @param jmhVersion the jmh version to get the names for
     * @return all jvm names
     */
    @GetMapping("/jmhVersion/forJmhVersion")
    public List<String> getAllJvmNamesForJmhVersion(@RequestParam final String jmhVersion) {
        return environmentService.getAll().stream()
                .filter(environment -> Objects.equals(environment.jmhVersion(), jmhVersion))
                .map(Environment::jvmName)
                .filter(jvmName -> jvmName != null && !jvmName.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all git origins that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all git origins
     */
    @GetMapping("/gitOrigins")
    public List<String> getAllGitOrigins() {
        return environmentService.getAll().stream()
                .map(Environment::gitOriginUrl)
                .filter(gitOriginUrl -> gitOriginUrl != null && !gitOriginUrl.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all git branches that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all git branches
     */
    @GetMapping("/gitBranches")
    public List<String> getAllGitBranches() {
        return environmentService.getAll().stream()
                .map(Environment::gitBranch)
                .filter(gitBranch -> gitBranch != null && !gitBranch.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all git branches for a specific git origin that are currently part of environments (for the current
     * tenant - see #{@link EnvironmentService}).
     *
     * @param gitOrigin the git origin to get the branches for
     * @return all git branches for the specified git origin
     */
    @GetMapping("/gitBranches/forGitOrigin")
    public List<String> getAllGitBranches(@RequestParam final String gitOrigin) {
        return environmentService.getAll().stream()
                .filter(environment -> Objects.equals(environment.gitOriginUrl(), gitOrigin))
                .map(Environment::gitBranch)
                .filter(gitBranch -> gitBranch != null && !gitBranch.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all human-readable system memory descriptions that are currently part of environments (for the current
     * tenant - see #{@link EnvironmentService}).
     *
     * @return all human-readable system memory descriptions
     */
    @GetMapping("/systemMemoryReadable")
    public List<SystemMemory> getAllSystemMemoryReadable() {
        return environmentService.getAll().stream()
                .map(Environment::systemMemory)
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all system processors that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all system processors
     */
    @GetMapping("/systemProcessors")
    public List<Integer> getAllSystemProcessorsCounts() {
        return environmentService.getAll().stream()
                .map(Environment::systemProcessors)
                .filter(processors -> processors != null)
                .distinct()
                .sorted()
                .toList();
    }

    /**
     * Get a list of all operating system names that are currently part of environments (for the current tenant - see
     * #{@link EnvironmentService}).
     *
     * @return all operating system names
     */
    @GetMapping("/osName")
    public List<String> getOperatingSystemNames() {
        return environmentService.getAll().stream()
                .map(Environment::osName)
                .filter(osName -> osName != null && !osName.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

}
