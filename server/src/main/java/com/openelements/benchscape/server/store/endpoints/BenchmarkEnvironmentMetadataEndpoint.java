package com.openelements.benchscape.server.store.endpoints;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.services.EnvironmentService;
import java.util.List;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(V2 + "/environment/metadata")
public class BenchmarkEnvironmentMetadataEndpoint {

    private final EnvironmentService environmentService;

    public BenchmarkEnvironmentMetadataEndpoint(EnvironmentService environmentService) {
        this.environmentService = environmentService;
    }

    @GetMapping("/os")
    public List<String> getAllOperationSystems() {
        return environmentService.getAll().stream()
                .map(Environment::osName)
                .filter(osName -> osName != null && !osName.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/osVersion")
    public List<String> getAllOperationSystemsVersions() {
        return environmentService.getAll().stream()
                .map(Environment::osVersion)
                .filter(osVersion -> osVersion != null && !osVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/osVersion/forOs")
    public List<String> getAllOperationSystemsVersions(@RequestParam final String osName) {
        return environmentService.getAll().stream()
                .filter(environment -> environment.osName().equals(osName))
                .map(Environment::osVersion)
                .filter(osVersion -> osVersion != null && !osVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/arch")
    public List<String> getAllArchitectures() {
        return environmentService.getAll().stream()
                .map(Environment::systemArch)
                .filter(systemArch -> systemArch != null && !systemArch.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/cores")
    public List<Integer> getAllCpuCores() {
        return environmentService.getAll().stream()
                .map(Environment::systemProcessors)
                .filter(cores -> cores != null)
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/memory")
    public List<Long> getAllMemory() {
        return environmentService.getAll().stream()
                .map(Environment::systemMemory)
                .filter(memory -> memory != null)
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/jvmName")
    public List<String> getAllJvmNames() {
        return environmentService.getAll().stream()
                .map(Environment::jvmName)
                .filter(jvmName -> jvmName != null && !jvmName.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/jvmVersion")
    public List<String> getAllJvmVersions() {
        return environmentService.getAll().stream()
                .map(Environment::jvmVersion)
                .filter(jvmVersion -> jvmVersion != null && !jvmVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/jvmVersion/forJvmName")
    public List<String> getAllJvmVersions(@RequestParam final String jvmName) {
        return environmentService.getAll().stream()
                .filter(environment -> environment.jvmName().equals(jvmName))
                .map(Environment::jvmVersion)
                .filter(jvmVersion -> jvmVersion != null && !jvmVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/jmhVersion")
    public List<String> getAllJvhVersions() {
        return environmentService.getAll().stream()
                .map(Environment::jmhVersion)
                .filter(jmhVersion -> jmhVersion != null && !jmhVersion.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

    @GetMapping("/gitOrigins")
    public List<String> getAllGitOrigins() {
        return environmentService.getAll().stream()
                .map(Environment::gitOriginUrl)
                .filter(gitOriginUrl -> gitOriginUrl != null && !gitOriginUrl.isBlank())
                .distinct()
                .sorted()
                .toList();
    }

}
