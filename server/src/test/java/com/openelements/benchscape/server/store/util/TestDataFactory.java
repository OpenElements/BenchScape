package com.openelements.benchscape.server.store.util;

import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.data.MeasurementMetadata;
import java.util.Map;
import java.util.Set;

public class TestDataFactory {

    public static MeasurementMetadata createMeasurementMetadataWithGitOriginUrl(String gitOriginUrl) {
        return new MeasurementMetadata(null, gitOriginUrl,
                null, null,
                Set.of(), null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, Map.of(), Map.of());
    }

    public static MeasurementMetadata createMeasurementMetadata() {
        return new MeasurementMetadata(null, null,
                null, null,
                Set.of(), null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, null,
                null, Map.of(), Map.of());
    }

    public static Environment createEnvironment() {
        return createEnvironment("test", "description");
    }

    public static Environment createEnvironmentWithGitOriginUrl(String gitOriginUrl) {
        return new Environment(null, "test", null, gitOriginUrl,
                null, null, null,
                null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null);
    }

    public static Environment createEnvironment(String name) {
        return createEnvironment(name, "description");
    }

    public static Environment createEnvironment(String name, String description) {
        return new Environment(null, name, description, null,
                null, null, null,
                null, null, null,
                null, null, null,
                null, null, null,
                null, null, null, null, null);
    }
}
