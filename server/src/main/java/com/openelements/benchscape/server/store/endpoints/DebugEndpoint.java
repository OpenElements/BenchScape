package com.openelements.benchscape.server.store.endpoints;

import static com.openelements.benchscape.server.store.endpoints.EndpointsConstants.V2;

import com.openelements.benchscape.jmh.model.BenchmarkConfiguration;
import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import com.openelements.benchscape.jmh.model.BenchmarkExecutionMetadata;
import com.openelements.benchscape.jmh.model.BenchmarkExecutionResult;
import com.openelements.benchscape.jmh.model.BenchmarkGitState;
import com.openelements.benchscape.jmh.model.BenchmarkInfrastructure;
import com.openelements.benchscape.jmh.model.BenchmarkMeasurementConfiguration;
import com.openelements.benchscape.jmh.model.BenchmarkType;
import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import com.openelements.benchscape.server.store.entities.BenchmarkEntity;
import com.openelements.benchscape.server.store.entities.EnvironmentEntity;
import com.openelements.benchscape.server.store.entities.MeasurementEntity;
import com.openelements.benchscape.server.store.entities.MeasurementMetadataEntity;
import com.openelements.benchscape.server.store.repositories.BenchmarkRepository;
import com.openelements.benchscape.server.store.repositories.EnvironmentRepository;
import com.openelements.benchscape.server.store.repositories.MeasurementMetadataRepository;
import com.openelements.benchscape.server.store.repositories.MeasurementRepository;
import com.openelements.benchscape.server.store.services.BenchmarkExecutionService;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(V2 + "/debug")
public class DebugEndpoint {

    private final BenchmarkRepository benchmarkRepository;

    private final EnvironmentRepository environmentRepository;

    private final MeasurementMetadataRepository measurementMetadataRepository;

    private final MeasurementRepository measurementRepository;

    private final BenchmarkExecutionService benchmarkExecutionService;


    @Autowired
    public DebugEndpoint(@NonNull final BenchmarkRepository benchmarkRepository,
            @NonNull final EnvironmentRepository environmentRepository,
            @NonNull final MeasurementMetadataRepository measurementMetadataRepository,
            @NonNull final MeasurementRepository measurementRepository,
            BenchmarkExecutionService benchmarkExecutionService) {
        this.benchmarkRepository = Objects.requireNonNull(benchmarkRepository, "benchmarkRepository must not be null");
        this.environmentRepository = Objects.requireNonNull(environmentRepository,
                "environmentRepository must not be null");
        this.measurementMetadataRepository = Objects.requireNonNull(measurementMetadataRepository,
                "measurementMetadataRepository must not be null");
        this.measurementRepository = Objects.requireNonNull(measurementRepository,
                "measurementRepository must not be null");
        this.benchmarkExecutionService = benchmarkExecutionService;
    }

    @GetMapping("/benchmarks")
    public List<BenchmarkEntity> getAllBenchmarkEntities() {
        return benchmarkRepository.findAll();
    }

    @GetMapping("/environments")
    public List<EnvironmentEntity> getAllEnvironmentEntities() {
        return environmentRepository.findAll();
    }

    @GetMapping("/measurementmetadata")
    public List<MeasurementMetadataEntity> getAllMeasurementMetadataEntities() {
        return measurementMetadataRepository.findAll();
    }

    @GetMapping("/measurements")
    public List<MeasurementEntity> getAllMeasurementEntities() {
        return measurementRepository.findAll().stream()
                .peek(e -> e.setMetadata(null))
                .collect(Collectors.toList());
    }

    @GetMapping("/measurementCount")
    public long getMeasurementCount() {
        return measurementRepository.count();
    }

    @Transactional
    @PostMapping("/create-test-data")
    public void createTestData() {

        Random random = new Random(System.currentTimeMillis());

        Stream.of("test-1", "test-2", "test-3")
                .forEach(name -> {
                    if (random.nextBoolean() || random.nextBoolean()) {
                        IntStream.of(4, 16)
                                .forEach(availableProcessors -> {
                                    if (random.nextBoolean() || random.nextBoolean()) {
                                        Stream.of("10.15.7", "11.0.8")
                                                .forEach(osVersion -> {
                                                    if (random.nextBoolean()) {
                                                        Stream.of("17.0.1", "21.0.1")
                                                                .forEach(jvmVersion -> {
                                                                    if (random.nextBoolean()) {
                                                                        double baseValue = random.nextDouble() * 1000.0;
                                                                        Instant startTime = Instant.now()
                                                                                .plusSeconds(
                                                                                        random.nextLong(
                                                                                                60 * 60 * 24 * 7));
                                                                        IntStream.range(0, 100 + random.nextInt(200))
                                                                                .mapToObj(i -> startTime.plusSeconds(
                                                                                        random.nextLong(60 * 60 * 24)))
                                                                                .forEach(time -> {
                                                                                    if (random.nextBoolean()
                                                                                            || random.nextBoolean()) {
                                                                                        double value =
                                                                                                baseValue +
                                                                                                        random.nextDouble()
                                                                                                                * 100.0;
                                                                                        BenchmarkExecution execution = create(
                                                                                                name,
                                                                                                time,
                                                                                                availableProcessors,
                                                                                                osVersion,
                                                                                                jvmVersion,
                                                                                                value);
                                                                                        benchmarkExecutionService.store(
                                                                                                execution);
                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }

    private BenchmarkExecution create(String name, Instant startTime, int availableProcessors, String osVersion,
            String jvmVersion,
            double value) {
        BenchmarkInfrastructure infrastructure = new BenchmarkInfrastructure("amd64", availableProcessors,
                16L * 1024L * 1024L * 1024L,
                "Mac OS X",
                osVersion, jvmVersion, "Temurin", Map.of(), Map.of(), "1.33");
        BenchmarkGitState gitState = new BenchmarkGitState("https://github.com/OpenElements/BenchScape", "main",
                UUID.randomUUID().toString(), Set.of(), false);
        BenchmarkConfiguration configuration = new BenchmarkConfiguration(1, 1, 1_000,
                TimeUnit.MILLISECONDS,
                new BenchmarkMeasurementConfiguration(5, 1_000, TimeUnit.MILLISECONDS, 1),
                new BenchmarkMeasurementConfiguration(5, 1_000, TimeUnit.MILLISECONDS, 1));
        BenchmarkExecutionMetadata metadata = new BenchmarkExecutionMetadata(startTime, startTime.plusSeconds(10),
                startTime.plusSeconds(20), startTime.plusSeconds(80),
                12, 12);
        Map<String, String> parameters = Map.of();
        BenchmarkExecutionResult result = new BenchmarkExecutionResult(value, (Math.random() / 10.0) * value,
                value - (Math.random() / 10.0) * value, value + (Math.random() / 10.0) * value,
                BenchmarkUnit.OPERATIONS_PER_MILLISECOND);
        return new BenchmarkExecution(name, BenchmarkType.THROUGHPUT,
                infrastructure,
                gitState,
                configuration,
                metadata,
                parameters,
                result);
    }
}
