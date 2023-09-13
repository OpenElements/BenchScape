package com.openelements.jmh.client.io;

import com.openelements.jmh.client.json.BenchmarkJsonFactory;
import com.openelements.jmh.common.BenchmarkExecution;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.Collection;
import java.util.Objects;

/**
 * Uploads benchmark results (as JSON) to a http endpoint.
 */
public class RestClient {

    public static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    public static final String CONTENT_TYPE_HEADER_VALUE = "application/json";
    public static final String LOCALHOST = "http://localhost:8080";
    private final String baseUrl;

    /**
     * Creates a new instance.
     *
     * @param baseUrl the base url of the endpoint
     */
    public RestClient(@NonNull String baseUrl) {
        this.baseUrl = Objects.requireNonNull(baseUrl, "baseUrl must not be null");
    }

    /**
     * Creates a new instance that uses {@code http://localhost:8080} as base url for the endpoint.
     */
    public RestClient() {
        this(LOCALHOST);
    }

    /***
     * Posts the benchmark execution to the endpoint.
     * @param benchmarkExecution the benchmark execution
     * @throws Exception if an error occurs
     */
    public void post(@NonNull BenchmarkExecution benchmarkExecution) throws Exception {
        try {
            final String json = BenchmarkJsonFactory.toJson(benchmarkExecution);
            post(json);
        } catch (final Exception e) {
            throw new Exception("Error in posting data", e);
        }
    }

    /**
     * Posts the json to the endpoint.
     *
     * @param json the json
     * @throws Exception if an error occurs
     */
    private void post(@NonNull String json) throws URISyntaxException, IOException, InterruptedException {
        final BodyPublisher bodyPublisher = HttpRequest.BodyPublishers.ofString(json);
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(getUrl())
                .header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE)
                .POST(bodyPublisher)
                .build();

        final HttpClient httpClient = HttpClient.newBuilder().build();
        final BodyHandler<Void> bodyHandler = HttpResponse.BodyHandlers.discarding();
        final HttpResponse<Void> response = httpClient.send(request, bodyHandler);
        System.out.println(response);
    }

    /**
     * Posts the benchmark executions to the endpoint.
     *
     * @param benchmarkExecutions the benchmark executions
     */
    public void post(@NonNull Collection<BenchmarkExecution> benchmarkExecutions) throws Exception {
        Objects.requireNonNull(benchmarkExecutions, "benchmarkExecutions must not be null");
        for (final BenchmarkExecution benchmarkExecution : benchmarkExecutions) {
            post(benchmarkExecution);
        }
    }

    /**
     * Returns the url of the endpoint.
     *
     * @return the url
     * @throws URISyntaxException if the url is invalid
     */
    @NonNull
    private URI getUrl() throws URISyntaxException {
        if (baseUrl.endsWith("/")) {
            return new URI(baseUrl + "api/benchmark");
        } else {
            return new URI(baseUrl + "/api/benchmark");
        }
    }

}
