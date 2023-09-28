package com.openelements.benchscape.jmh.client.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openelements.benchscape.jmh.client.json.BenchmarkJsonFactory;
import com.openelements.benchscape.jmh.model.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

public class BenchmarkJsonFactoryTest {

    @Test
    void testBenchmarkExecutionToJson() {
        // Given
        final BenchmarkExecution execution = DummyFactory.createBenchmark();

        // When
        final String json = BenchmarkJsonFactory.toJson(execution);
        JsonElement jsonElement = new JsonParser().parse(json);

        // Then
        Assertions.assertNotNull(jsonElement);
        Assertions.assertTrue(jsonElement.isJsonObject());

        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        Assertions.assertEquals("test-benchmark", jsonObject.get("benchmarkName").getAsString());
        Assertions.assertEquals("THROUGHPUT", jsonObject.get("type").getAsString());
        Assertions.assertNotNull(jsonObject.get("infrastructure"));

        final JsonObject infrastructure = jsonObject.get("infrastructure").getAsJsonObject();
        Assertions.assertEquals("test-chip-architecture", infrastructure.get("arch").getAsString());
        Assertions.assertEquals(4, infrastructure.get("availableProcessors").getAsInt());
        Assertions.assertEquals(1024, infrastructure.get("memory").getAsLong());
    }

    @Test
    void testBenchmarkConfigurationToJson() {
        // Given
        final BenchmarkConfiguration configuration = DummyFactory.createBenchmarkConfiguration();

        // When
        final String json = BenchmarkJsonFactory.toJson(configuration);
        JsonElement jsonElement = new JsonParser().parse(json);

        // Then
        Assertions.assertNotNull(jsonElement);
        Assertions.assertTrue(jsonElement.isJsonObject());

        final JsonObject jsonObject = jsonElement.getAsJsonObject();
        Assertions.assertEquals(1, jsonObject.get("threads").getAsInt());
        Assertions.assertEquals(1, jsonObject.get("forks").getAsInt());
        Assertions.assertEquals(1, jsonObject.get("timeout").getAsLong());
        Assertions.assertEquals("SECONDS", jsonObject.get("timeoutUnit").getAsString());

        final JsonObject measurementConfiguration = jsonObject.get("measurementConfiguration").getAsJsonObject();
        Assertions.assertEquals(1, measurementConfiguration.get("iterations").getAsInt());
        Assertions.assertEquals(1, measurementConfiguration.get("time").getAsLong());
        Assertions.assertEquals("SECONDS", measurementConfiguration.get("timeUnit").getAsString());
        Assertions.assertEquals(1, measurementConfiguration.get("batchSize").getAsInt());
    }

    @Test
    void testBenchmarkInfrastructureToJson() {
        // Given
        final BenchmarkInfrastructure infrastructure = DummyFactory.createBenchmarkInfrastructure();

        // When
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("arch", infrastructure.arch());
        jsonObject.addProperty("availableProcessors", infrastructure.availableProcessors());
        jsonObject.addProperty("memory", infrastructure.memory());
        jsonObject.addProperty("osName", infrastructure.osName());
        jsonObject.addProperty("osVersion", infrastructure.osVersion());
        jsonObject.addProperty("jvmVersion", infrastructure.jvmVersion());
        jsonObject.addProperty("jvmName", infrastructure.jvmName());
        jsonObject.addProperty("jmhVersion", infrastructure.jmhVersion());

        // Then
        final String json = jsonObject.toString();
        JsonElement jsonElement = new JsonParser().parse(json);

        Assertions.assertNotNull(jsonElement);
        Assertions.assertTrue(jsonElement.isJsonObject());

        final JsonObject resultObject = jsonElement.getAsJsonObject();
        Assertions.assertEquals("test-chip-architecture", resultObject.get("arch").getAsString());
        Assertions.assertEquals(4, resultObject.get("availableProcessors").getAsInt());
        Assertions.assertEquals(1024, resultObject.get("memory").getAsLong());
        Assertions.assertEquals("osName", resultObject.get("osName").getAsString());
        Assertions.assertEquals("osVersion", resultObject.get("osVersion").getAsString());
        Assertions.assertEquals("jvmVersion", resultObject.get("jvmVersion").getAsString());
        Assertions.assertEquals("jvmName", resultObject.get("jvmName").getAsString());
        Assertions.assertEquals("jmhVendor", resultObject.get("jmhVersion").getAsString());
    }

    @Test
    void testBenchmarkMeasurementConfigurationToJson() {
        // Given
        final BenchmarkMeasurementConfiguration measurementConfiguration = DummyFactory.createBenchmarkMeasurementConfiguration();

        // When
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("iterations", measurementConfiguration.iterations());
        jsonObject.addProperty("time", measurementConfiguration.time());
        jsonObject.addProperty("timeUnit", measurementConfiguration.timeUnit().toString());
        jsonObject.addProperty("batchSize", measurementConfiguration.batchSize());

        // Then
        final String json = jsonObject.toString();
        JsonElement jsonElement = new JsonParser().parse(json);

        Assertions.assertNotNull(jsonElement);
        Assertions.assertTrue(jsonElement.isJsonObject());

        final JsonObject resultObject = jsonElement.getAsJsonObject();
        Assertions.assertEquals(1, resultObject.get("iterations").getAsInt());
        Assertions.assertEquals(1, resultObject.get("time").getAsLong());
        Assertions.assertEquals("SECONDS", resultObject.get("timeUnit").getAsString());
        Assertions.assertEquals(1, resultObject.get("batchSize").getAsInt());
    }

    @Test
    void testBenchmarkExecutionResultToJson() {
        // Given
        final BenchmarkExecutionResult result = DummyFactory.createBenchmarkResult();

        // When
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("value", result.value());
        jsonObject.addProperty("error", result.error());
        jsonObject.addProperty("min", result.min());
        jsonObject.addProperty("max", result.max());
        jsonObject.addProperty("unit", result.unit().toString());

        // Then
        final String json = jsonObject.toString();
        JsonElement jsonElement = new JsonParser().parse(json);

        Assertions.assertNotNull(jsonElement);
        Assertions.assertTrue(jsonElement.isJsonObject());

        final JsonObject resultObject = jsonElement.getAsJsonObject();
        Assertions.assertEquals(1.0d, resultObject.get("value").getAsDouble());
        Assertions.assertEquals(1.0d, resultObject.get("error").getAsDouble());
        Assertions.assertEquals(1.0d, resultObject.get("min").getAsDouble());
        Assertions.assertEquals(1.0d, resultObject.get("max").getAsDouble());
        Assertions.assertEquals("OPERATIONS_PER_MILLISECOND", resultObject.get("unit").getAsString());
    }
}
