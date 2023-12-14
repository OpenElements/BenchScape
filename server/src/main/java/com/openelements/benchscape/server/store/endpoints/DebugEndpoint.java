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
import com.openelements.benchscape.server.store.repositories.EnvironmentRepository;
import com.openelements.benchscape.server.store.services.BenchmarkExecutionService;
import com.openelements.server.base.tenant.TenantService;
import edu.umd.cs.findbugs.annotations.NonNull;
import jakarta.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
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

    private final EnvironmentRepository environmentRepository;

    private final BenchmarkExecutionService benchmarkExecutionService;

    private final TenantService tenantService;

    private final EntityManager entityManager;

    @Autowired
    public DebugEndpoint(@NonNull final EnvironmentRepository environmentRepository,
            BenchmarkExecutionService benchmarkExecutionService, TenantService tenantService,
            EntityManager entityManager) {
        this.environmentRepository = Objects.requireNonNull(environmentRepository,
                "environmentRepository must not be null");

        this.benchmarkExecutionService = Objects.requireNonNull(benchmarkExecutionService,
                "benchmarkExecutionService must not be null");
        this.tenantService = Objects.requireNonNull(tenantService, "tenantService must not be null");
        this.entityManager = Objects.requireNonNull(entityManager, "entityManager must not be null");
    }

    @GetMapping("/benchmarks")
    public List<BenchmarkEntity> getAllBenchmarkEntities() {
        return entityManager.createQuery("SELECT b FROM Benchmark b", BenchmarkEntity.class).getResultList();
    }

    @GetMapping("/environments")
    public List<EnvironmentEntity> getAllEnvironmentEntities() {
        return entityManager.createQuery("SELECT e FROM Environment e", EnvironmentEntity.class).getResultList();
    }

    @GetMapping("/measurementmetadata")
    public List<MeasurementMetadataEntity> getAllMeasurementMetadataEntities() {
        return entityManager.createQuery("SELECT m FROM MeasurementMetadata m", MeasurementMetadataEntity.class)
                .getResultList();
    }

    @GetMapping("/measurements")
    public List<MeasurementEntity> getAllMeasurementEntities() {
        return entityManager.createQuery("SELECT m FROM Measurement m", MeasurementEntity.class).getResultList()
                .stream()
                .peek(e -> e.setMetadata(null))
                .collect(Collectors.toList());
    }

    @GetMapping("/measurementCount")
    public long getMeasurementCount() {
        return entityManager.createQuery("SELECT COUNT(m) FROM Measurement m", Long.class).getSingleResult();
    }

    @Transactional
    @PostMapping("/create-test-data")
    public void createTestData() {

        createDemoEnvironments();

        Stream.of("test-1", "test-2", "test-3")
                .forEach(name -> createMeasurements(name));
    }

    private void createMeasurements(String name) {
        Random random = new Random(System.currentTimeMillis());
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
                                                                    .minus(
                                                                            random.nextInt(300),
                                                                            ChronoUnit.DAYS);
                                                            IntStream.range(0, 100 + random.nextInt(200))
                                                                    .mapToObj(i -> startTime.plusSeconds(
                                                                                    random.nextLong(60 * 60))
                                                                            .plus(i, ChronoUnit.DAYS))
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
    }

    private void createDemoEnvironments() {
        if (getAllEnvironmentEntities().isEmpty()) {
            EnvironmentEntity entityMac1 = new EnvironmentEntity();
            entityMac1.setName("benchScape-main-mac-14");
            entityMac1.setDescription("BenchScape main branch on MacOS");
            entityMac1.setGitOriginUrl("https://github.com/OpenElements/BenchScape");
            entityMac1.setGitBranch("main");
            entityMac1.setOsName("Mac OS X");
            entityMac1.setOsVersion("14.1.1");
            entityMac1.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entityMac1);

            EnvironmentEntity entityMac2 = new EnvironmentEntity();
            entityMac2.setName("benchScape-main-mac-13");
            entityMac2.setDescription("BenchScape main branch on MacOS");
            entityMac2.setGitOriginUrl("https://github.com/OpenElements/BenchScape");
            entityMac2.setGitBranch("main");
            entityMac2.setOsName("Mac OS X");
            entityMac2.setOsVersion("13.0.0");
            entityMac2.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entityMac2);

            EnvironmentEntity entity2 = new EnvironmentEntity();
            entity2.setName("benchScape-main-win");
            entity2.setDescription("BenchScape main branch on Windows");
            entity2.setGitOriginUrl("https://github.com/OpenElements/BenchScape");
            entity2.setGitBranch("main");
            entity2.setOsName("Windows");
            entity2.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entity2);

            EnvironmentEntity entity3 = new EnvironmentEntity();
            entity3.setName("benchScape-main-linux");
            entity3.setDescription("BenchScape main branch on Linux");
            entity3.setGitOriginUrl("https://github.com/OpenElements/BenchScape");
            entity3.setGitBranch("main");
            entity3.setOsName("Linux");
            entity3.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entity3);

            EnvironmentEntity entity4 = new EnvironmentEntity();
            entity4.setName("4-plus-cores");
            entity4.setDescription("Execution on machine with more than 4 cores");
            entity4.setSystemProcessorsMin(4);
            entity4.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entity4);

            EnvironmentEntity entity5 = new EnvironmentEntity();
            entity5.setName("less-32-cores");
            entity5.setDescription("Execution on machine with less than 32 cores");
            entity5.setSystemProcessorsMax(32);
            entity5.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entity5);

            EnvironmentEntity entity6 = new EnvironmentEntity();
            entity6.setName("java-17");
            entity6.setDescription("Execution with Java 17");
            entity6.setJvmName("Eclipse Temurin");
            entity6.setJvmVersion("17.0.1");
            entity6.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entity6);

            EnvironmentEntity entityOnlyJavaVersion = new EnvironmentEntity();
            entityOnlyJavaVersion.setName("Only Java Version");
            entityOnlyJavaVersion.setDescription("No Java name but a version");
            entityOnlyJavaVersion.setJvmVersion("21.0.2");
            entityOnlyJavaVersion.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entityOnlyJavaVersion);

            EnvironmentEntity entityOnlyJavaName = new EnvironmentEntity();
            entityOnlyJavaName.setName("Only Java Name");
            entityOnlyJavaName.setDescription("Only Java name and no version");
            entityOnlyJavaName.setJvmName("Bellsoft Liberica");
            entityOnlyJavaName.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entityOnlyJavaName);

            EnvironmentEntity entityOnlyOsVersion = new EnvironmentEntity();
            entityOnlyOsVersion.setName("Only OS Version");
            entityOnlyOsVersion.setDescription("No OS name but a version");
            entityOnlyOsVersion.setOsVersion("3.4.5");
            entityOnlyOsVersion.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entityOnlyOsVersion);

            EnvironmentEntity entityOnlyOSName = new EnvironmentEntity();
            entityOnlyOSName.setName("Only OS Name");
            entityOnlyOSName.setDescription("Only OS name and no version");
            entityOnlyOSName.setOsName("MacOS");
            entityOnlyOSName.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entityOnlyOSName);

            EnvironmentEntity entityOnlyArch = new EnvironmentEntity();
            entityOnlyArch.setName("Only Arch");
            entityOnlyArch.setDescription("Only Arch value");
            entityOnlyArch.setSystemArch("intel64");
            entityOnlyArch.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entityOnlyArch);

            EnvironmentEntity entityOnlyCores = new EnvironmentEntity();
            entityOnlyCores.setName("Only Cores");
            entityOnlyCores.setDescription("Only Cores value");
            entityOnlyCores.setSystemProcessors(32);
            entityOnlyCores.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entityOnlyCores);

            EnvironmentEntity entityOnlyMemory = new EnvironmentEntity();
            entityOnlyMemory.setName("Only Memory");
            entityOnlyMemory.setDescription("Only Memory value");
            entityOnlyMemory.setSystemMemory(68_719_476_736L);
            entityOnlyMemory.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entityOnlyMemory);

            EnvironmentEntity entityFull = new EnvironmentEntity();
            entityFull.setName("A concrete system");
            entityFull.setDescription("A system with all definitions");
            entityFull.setOsName("Linux");
            entityFull.setOsVersion("5.4.0-90-generic");
            entityFull.setSystemProcessors(8);
            entityFull.setSystemMemory(68_719_476_736L);
            entityFull.setSystemArch("amd64");
            entityFull.setGitOriginUrl("https://github.com/OpenElements/BenchScape");
            entityFull.setGitBranch("main");
            entityFull.setJvmName("Eclipse Temurin");
            entityFull.setJvmVersion("21.0.1");
            entityFull.setJmhVersion("1.37");
            entityFull.setTenantId(tenantService.getCurrentTenant());
            environmentRepository.save(entityFull);
        }
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
