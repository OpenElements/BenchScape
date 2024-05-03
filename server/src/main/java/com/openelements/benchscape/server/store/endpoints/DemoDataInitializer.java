package com.openelements.benchscape.server.store.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DemoDataInitializer implements ApplicationRunner {

    private final DebugEndpoint debugEndpoint;

    @Autowired
    public DemoDataInitializer(DebugEndpoint debugEndpoint) {
        this.debugEndpoint = debugEndpoint;
    }

    @Override
    public void run(ApplicationArguments args) {
        int numberOfRuns = 5;

        for (int i = 0; i < numberOfRuns; i++) {
            debugEndpoint.createTestData();
        }
    }
}