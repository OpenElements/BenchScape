package com.openelements.benchscape.jmh.client.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.openelements.benchscape.jmh.client.json.BenchmarkJsonFactory;
import com.openelements.benchscape.jmh.model.BenchmarkExecution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BenchmarkJsonFactoryTest {

    @Test
    void test() {
        //given
        final BenchmarkExecution execution = DummyFactory.createBenchmark();

        //when
        final String json = BenchmarkJsonFactory.toJson(execution);
        JsonElement jsonElement = new JsonParser().parse(json);

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
}
