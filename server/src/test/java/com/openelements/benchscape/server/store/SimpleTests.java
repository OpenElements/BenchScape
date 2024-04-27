package com.openelements.benchscape.server.store;

import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.endpoints.BenchmarkEndpoint;
import com.openelements.benchscape.server.store.endpoints.BenchmarkEnvironmentEndpoint;
import com.openelements.benchscape.server.store.util.SpringTestSupportService;
import com.openelements.benchscape.server.tenant.SimpleTenantConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {StoreConfig.class, SimpleTenantConfig.class})
public class SimpleTests {

    @Autowired
    private BenchmarkEndpoint endpoint;

    @Autowired
    private BenchmarkEnvironmentEndpoint environmentEndpoint;

    @Autowired
    private SpringTestSupportService testSupportService;

    @BeforeEach
    public void init() {
        testSupportService.clearDatabase();
    }

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertNotNull(endpoint);
    }

    @Test
    public void dbCallWorks() throws Exception {
        Assertions.assertNotNull(endpoint.getAll());
    }

    @Test
    public void SavingAnEntityWorks() throws Exception {
        //given
        Environment environment = new Environment(null, "test", null, null,
                null, null,
                null, null,
                null, null,
                null, null, null,
                null, null,
                null, null,
                null);

        //when
        environmentEndpoint.save(environment);

        //then
        Assertions.assertEquals(1, environmentEndpoint.getAll().size());
    }
}
