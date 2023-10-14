package com.openelements.jmh.store.v2;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = ConfigV2.class)
public class SimpleTest {

    @Autowired
    private BenchmarkEndpoint endpoint;

    @Test
    public void contextLoads() throws Exception {
        Assertions.assertNotNull(endpoint);
    }

    @Test
    public void dbCallWorks() throws Exception {
        Assertions.assertNotNull(endpoint.getAll());
    }
}
