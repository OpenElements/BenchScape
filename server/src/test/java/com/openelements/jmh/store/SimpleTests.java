package com.openelements.jmh.store;

import com.openelements.jmh.store.store.StoreConfig;
import com.openelements.jmh.store.store.data.Environment;
import com.openelements.jmh.store.store.endpoints.BenchmarkEndpoint;
import com.openelements.jmh.store.store.endpoints.BenchmarkEnvironmentEndpoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = StoreConfig.class)
public class SimpleTests {

    @Autowired
    private BenchmarkEndpoint endpoint;

    @Autowired
    private BenchmarkEnvironmentEndpoint environmentEndpoint;

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
                null, null,
                null, null,
                null, null,
                null);

        //when
        environmentEndpoint.save(environment);

        //then
        Assertions.assertEquals(1, environmentEndpoint.getAll().size());
    }
}
