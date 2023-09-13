package com.openelements.jmh.client;

import com.openelements.jmh.client.io.FileClient;
import com.openelements.jmh.client.io.RestClient;
import com.openelements.jmh.client.jmh.BenchmarkFactory;
import com.openelements.jmh.client.jmh.JmhExecutor;

public class Sample1 {

    public static void main(String[] args) throws Exception {
        final RestClient restClient = new RestClient();
        final FileClient fileClient = new FileClient("target/benchmark/");
        JmhExecutor.executeAll().stream()
                .map(BenchmarkFactory::convert)
                .forEach(benchmarkExecution -> {
                    try {
                        fileClient.write(benchmarkExecution);
                        restClient.post(benchmarkExecution);
                    } catch (final Exception e) {
                        throw new RuntimeException("Error in posting result", e);
                    }
                });
    }
}
