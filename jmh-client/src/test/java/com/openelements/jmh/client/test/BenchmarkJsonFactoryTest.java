package com.openelements.jmh.client.test;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.openelements.jmh.client.json.BenchmarkJsonFactory;
import com.openelements.jmh.common.BenchmarkExecution;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BenchmarkJsonFactoryTest {

    @Test
    void test() {
        //given
        final BenchmarkExecution execution = DummyFactory.createBenchmark();

        //when
        final JsonElement jsonElement = BenchmarkJsonFactory.toJsonTree(execution);

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
