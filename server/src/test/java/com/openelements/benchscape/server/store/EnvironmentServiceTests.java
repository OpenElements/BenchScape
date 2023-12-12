package com.openelements.benchscape.server.store;

import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.data.MeasurementMetadata;
import com.openelements.benchscape.server.store.repositories.EnvironmentRepository;
import com.openelements.benchscape.server.store.services.EnvironmentService;
import com.openelements.benchscape.server.store.util.SpringTestSupportService;
import com.openelements.benchscape.server.store.util.TestDataFactory;
import com.openelements.benchscape.server.tenant.SimpleTenantConfig;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

@SpringBootTest(classes = {StoreConfig.class, SimpleTenantConfig.class})
public class EnvironmentServiceTests {

    @Autowired
    private EnvironmentService environmentService;

    @Autowired
    private EnvironmentRepository environmentRepository;

    @Autowired
    private SpringTestSupportService testSupportService;

    @BeforeEach
    public void init() {
        testSupportService.clearDatabase();
    }

    @Test
    void testSaveNullEnvironment() {
        //given
        final Environment environment = null;

        //then
        Assertions.assertThrows(NullPointerException.class, () -> environmentService.save(environment));
    }

    @Test
    void testSaveNewEnvironment() {
        //given
        final Environment environment = TestDataFactory.createEnvironment();

        //when
        final Environment saved = environmentService.save(environment);

        //then
        Assertions.assertNotNull(saved);
        Assertions.assertNotNull(saved.id());
        Assertions.assertEquals(1, environmentService.getAll().size());
        Assertions.assertEquals(saved, environmentService.getAll().get(0));

        Assertions.assertEquals(environment.name(), saved.name());
        Assertions.assertEquals(environment.description(), saved.description());
        Assertions.assertEquals(environment.gitBranch(), saved.gitBranch());
        Assertions.assertEquals(environment.gitOriginUrl(), saved.gitOriginUrl());
        Assertions.assertEquals(environment.jmhVersion(), saved.jmhVersion());
        Assertions.assertEquals(environment.jvmName(), saved.jvmName());
        Assertions.assertEquals(environment.jvmVersion(), saved.jvmVersion());
        Assertions.assertEquals(environment.name(), saved.name());
        Assertions.assertEquals(environment.osName(), saved.osName());
        Assertions.assertEquals(environment.osVersion(), saved.osVersion());
        Assertions.assertEquals(environment.systemArch(), saved.systemArch());
        Assertions.assertEquals(environment.systemMemory(), saved.systemMemory());
        Assertions.assertEquals(environment.systemMemoryMax(), saved.systemMemoryMax());
        Assertions.assertEquals(environment.systemMemoryMin(), saved.systemMemoryMin());
        Assertions.assertEquals(environment.systemProcessors(), saved.systemProcessors());
        Assertions.assertEquals(environment.systemProcessorsMax(), saved.systemProcessorsMax());
        Assertions.assertEquals(environment.systemProcessorsMin(), saved.systemProcessorsMin());
    }

    @Test
    void testUpdateEnvironment() {
        //given
        final Environment environment = TestDataFactory.createEnvironment();

        //when
        final Environment saved = environmentService.save(environment);
        final Environment updated = new Environment(saved.id(), saved.name(), saved.description(), saved.gitOriginUrl(),
                saved.gitBranch(), saved.systemArch(), saved.systemProcessors(), saved.systemProcessorsMin(),
                saved.systemProcessorsMax(), saved.systemMemory(), saved.systemMemoryMin(), saved.systemMemoryMax(),
                saved.jvmName(), saved.jvmVersion(), saved.osName(), saved.osVersion(),
                "NEW JMH VERSION");
        final Environment savedUpdated = environmentService.save(updated);

        //then
        Assertions.assertNotNull(savedUpdated);
        Assertions.assertNotNull(savedUpdated.id());
        Assertions.assertEquals(1, environmentService.getAll().size());
        Assertions.assertEquals(savedUpdated, environmentService.getAll().get(0));
        Assertions.assertEquals(saved.id(), savedUpdated.id());

        Assertions.assertEquals(environment.name(), savedUpdated.name());
        Assertions.assertEquals(environment.name(), savedUpdated.name());
        Assertions.assertEquals(environment.description(), savedUpdated.description());
        Assertions.assertEquals(environment.gitBranch(), savedUpdated.gitBranch());
        Assertions.assertEquals(environment.gitOriginUrl(), savedUpdated.gitOriginUrl());
        Assertions.assertEquals("NEW JMH VERSION", savedUpdated.jmhVersion());
        Assertions.assertEquals(environment.jvmName(), savedUpdated.jvmName());
        Assertions.assertEquals(environment.jvmVersion(), savedUpdated.jvmVersion());
        Assertions.assertEquals(environment.name(), savedUpdated.name());
        Assertions.assertEquals(environment.osName(), savedUpdated.osName());
        Assertions.assertEquals(environment.osVersion(), savedUpdated.osVersion());
        Assertions.assertEquals(environment.systemArch(), savedUpdated.systemArch());
        Assertions.assertEquals(environment.systemMemory(), savedUpdated.systemMemory());
        Assertions.assertEquals(environment.systemMemoryMax(), savedUpdated.systemMemoryMax());
        Assertions.assertEquals(environment.systemMemoryMin(), savedUpdated.systemMemoryMin());
        Assertions.assertEquals(environment.systemProcessors(), savedUpdated.systemProcessors());
        Assertions.assertEquals(environment.systemProcessorsMax(), savedUpdated.systemProcessorsMax());
        Assertions.assertEquals(environment.systemProcessorsMin(), savedUpdated.systemProcessorsMin());
    }

    @Test
    void testGetNotExistingEnvironment() {
        //given
        final UUID id = UUID.randomUUID();

        //when
        final Optional<Environment> environment = environmentService.get(id);

        //then
        Assertions.assertNotNull(environment);
        Assertions.assertFalse(environment.isPresent());
    }

    @Test
    void testGetNullId() {
        //given
        final UUID id = null;

        //then
        Assertions.assertThrows(NullPointerException.class, () -> environmentService.get(id));
    }

    @Test
    void testFindNullId() {
        //given
        final String id = null;

        //then
        Assertions.assertThrows(NullPointerException.class, () -> environmentService.find(id));
    }

    @Test
    void testFindNotExistingEnvironment() {
        //given
        final UUID id = UUID.randomUUID();

        //then
        Assertions.assertThrows(IllegalArgumentException.class, () -> environmentService.find(id.toString()));
    }

    @Test
    void testGetExistingEnvironment() {
        //given
        final Environment environment = TestDataFactory.createEnvironment();

        //when
        final UUID id = environmentService.save(environment).id();
        final Optional<Environment> savedEnv = environmentService.get(id);

        //then
        Assertions.assertNotNull(savedEnv);
        Assertions.assertTrue(savedEnv.isPresent());
        Assertions.assertEquals(id, savedEnv.get().id());
        Assertions.assertEquals(environment.name(), savedEnv.get().name());
        Assertions.assertEquals(environment.name(), savedEnv.get().name());
        Assertions.assertEquals(environment.description(), savedEnv.get().description());
        Assertions.assertEquals(environment.gitBranch(), savedEnv.get().gitBranch());
        Assertions.assertEquals(environment.gitOriginUrl(), savedEnv.get().gitOriginUrl());
        Assertions.assertEquals(environment.jmhVersion(), savedEnv.get().jmhVersion());
        Assertions.assertEquals(environment.jvmName(), savedEnv.get().jvmName());
        Assertions.assertEquals(environment.jvmVersion(), savedEnv.get().jvmVersion());
        Assertions.assertEquals(environment.name(), savedEnv.get().name());
        Assertions.assertEquals(environment.osName(), savedEnv.get().osName());
        Assertions.assertEquals(environment.osVersion(), savedEnv.get().osVersion());
        Assertions.assertEquals(environment.systemArch(), savedEnv.get().systemArch());
        Assertions.assertEquals(environment.systemMemory(), savedEnv.get().systemMemory());
        Assertions.assertEquals(environment.systemMemoryMax(), savedEnv.get().systemMemoryMax());
        Assertions.assertEquals(environment.systemMemoryMin(), savedEnv.get().systemMemoryMin());
        Assertions.assertEquals(environment.systemProcessors(), savedEnv.get().systemProcessors());
        Assertions.assertEquals(environment.systemProcessorsMax(), savedEnv.get().systemProcessorsMax());
        Assertions.assertEquals(environment.systemProcessorsMin(), savedEnv.get().systemProcessorsMin());
    }

    @Test
    void testFindExistingEnvironment() {
        //given
        final Environment environment = TestDataFactory.createEnvironment();

        //when
        final UUID id = environmentService.save(environment).id();
        final Environment savedEnv = environmentService.find(id.toString());

        //then
        Assertions.assertNotNull(savedEnv);
        Assertions.assertEquals(id, savedEnv.id());
        Assertions.assertEquals(environment.name(), savedEnv.name());
        Assertions.assertEquals(environment.name(), savedEnv.name());
        Assertions.assertEquals(environment.description(), savedEnv.description());
        Assertions.assertEquals(environment.gitBranch(), savedEnv.gitBranch());
        Assertions.assertEquals(environment.gitOriginUrl(), savedEnv.gitOriginUrl());
        Assertions.assertEquals(environment.jmhVersion(), savedEnv.jmhVersion());
        Assertions.assertEquals(environment.jvmName(), savedEnv.jvmName());
        Assertions.assertEquals(environment.jvmVersion(), savedEnv.jvmVersion());
        Assertions.assertEquals(environment.name(), savedEnv.name());
        Assertions.assertEquals(environment.osName(), savedEnv.osName());
        Assertions.assertEquals(environment.osVersion(), savedEnv.osVersion());
        Assertions.assertEquals(environment.systemArch(), savedEnv.systemArch());
        Assertions.assertEquals(environment.systemMemory(), savedEnv.systemMemory());
        Assertions.assertEquals(environment.systemMemoryMax(), savedEnv.systemMemoryMax());
        Assertions.assertEquals(environment.systemMemoryMin(), savedEnv.systemMemoryMin());
        Assertions.assertEquals(environment.systemProcessors(), savedEnv.systemProcessors());
        Assertions.assertEquals(environment.systemProcessorsMax(), savedEnv.systemProcessorsMax());
        Assertions.assertEquals(environment.systemProcessorsMin(), savedEnv.systemProcessorsMin());
    }

    @Test
    void testCanNotHaveMultipleEnvironmentsWithSameName() {
        //given
        final Environment environment1 = TestDataFactory.createEnvironment("test", "V1");
        final Environment environment2 = TestDataFactory.createEnvironment("test", "V2");

        //then
        Assertions.assertDoesNotThrow(() -> environmentService.save(environment1));
        Assertions.assertThrows(DataIntegrityViolationException.class, () -> environmentService.save(environment2));
    }

    @Test
    void testMatchWithNullMetadata() {
        //given
        final MeasurementMetadata metadata = null;
        final Environment environment = TestDataFactory.createEnvironment();

        //then
        Assertions.assertThrows(NullPointerException.class,
                () -> environmentService.isMatchingEnvironment(metadata, environment));
    }

    @Test
    void testMatchWithNullEnvironment() {
        //given
        final MeasurementMetadata metadata = TestDataFactory.createMeasurementMetadata();
        final Environment environment = null;

        //then
        Assertions.assertThrows(NullPointerException.class,
                () -> environmentService.isMatchingEnvironment(metadata, environment));
    }

    @Test
    void testMatchByNullGitOriginUrl() {
        //given
        final String gitOriginUrl = "https://github.com/OpenElements/BenchScape";
        final MeasurementMetadata metadata = TestDataFactory.createMeasurementMetadataWithGitOriginUrl(gitOriginUrl);
        final Environment environment = TestDataFactory.createEnvironmentWithGitOriginUrl(null);

        //when
        final boolean isMatching = environmentService.isMatchingEnvironment(metadata, environment);

        //then
        Assertions.assertTrue(isMatching);
    }

    @Test
    void testNotMatchByNullGitOriginUrl() {
        //given
        final String gitOriginUrl = "https://github.com/OpenElements/BenchScape";
        final MeasurementMetadata metadata = TestDataFactory.createMeasurementMetadataWithGitOriginUrl(null);
        final Environment environment = TestDataFactory.createEnvironmentWithGitOriginUrl(gitOriginUrl);

        //when
        final boolean isMatching = environmentService.isMatchingEnvironment(metadata, environment);

        //then
        Assertions.assertFalse(isMatching);
    }

    @Test
    void testMatchByGitOriginUrl() {
        //given
        final String gitOriginUrl = "https://github.com/OpenElements/BenchScape";
        final MeasurementMetadata metadata = TestDataFactory.createMeasurementMetadataWithGitOriginUrl(gitOriginUrl);
        final Environment environment = TestDataFactory.createEnvironmentWithGitOriginUrl(gitOriginUrl);

        //when
        final boolean isMatching = environmentService.isMatchingEnvironment(metadata, environment);

        //then
        Assertions.assertTrue(isMatching);
    }

    @ParameterizedTest
    @CsvSource({
            "https://github.com/OpenElements/BenchScape, https://github.com/OpenElements/website, false",
            "https://github.com/OpenElements/BenchScape, https://github.com/OpenElements/*,       true",
            "https://github.com/OpenElements/BenchScape, https://github.com/*/BenchScape,         true",
            "https://github.com/OpenElements/BenchScape, *,                                       true",
            "https://github.com/OpenElements/BenchScape, https://github.com/*/website,            false"
    })
    void testNoMatchByGitOriginUrl(final String measurementGitOriginUrl, final String environmentGitOriginUrl,
            final boolean expected) {
        //given
        final MeasurementMetadata metadata = TestDataFactory.createMeasurementMetadataWithGitOriginUrl(
                measurementGitOriginUrl);
        final Environment environment = TestDataFactory.createEnvironmentWithGitOriginUrl(environmentGitOriginUrl);

        //when
        final boolean isMatching = environmentService.isMatchingEnvironment(metadata, environment);

        //then
        Assertions.assertEquals(expected, isMatching);
    }
}
