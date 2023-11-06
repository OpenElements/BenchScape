package com.openelements.jmh.store.v2;

import com.openelements.jmh.store.v2.data.Environment;
import com.openelements.jmh.store.v2.endpoints.BenchmarkEndpoint;
import com.openelements.jmh.store.v2.endpoints.BenchmarkEnvironmentEndpoint;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ConfigV2.class)
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
