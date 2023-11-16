package com.openelements.jmh.store.util;

import com.openelements.jmh.store.store.data.Environment;
import com.openelements.jmh.store.store.data.MeasurementMetadata;
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
                null);
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
                null);
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
                null);
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
                null);
    }
}
