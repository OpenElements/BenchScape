package com.openelements.jmh.client.io;

import com.openelements.jmh.client.json.BenchmarkJsonFactory;
import com.openelements.jmh.common.BenchmarkExecution;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublisher;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.util.Objects;

public class RestClient {

    public static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    public static final String CONTENT_TYPE_HEADER_VALUE = "application/json";
    public static final String LOCALHOST = "http://localhost:8080";
    private final String baseUrl;

    public RestClient(@NonNull String baseUrl) {
        this.baseUrl = Objects.requireNonNull(baseUrl, "baseUrl must not be null");
    }

    public RestClient() {
        this(LOCALHOST);
    }

    public void post(@NonNull BenchmarkExecution benchmarkExecution) throws Exception {
        final String json = BenchmarkJsonFactory.toJson(benchmarkExecution);
        post(json);
    }

    private void post(@NonNull String json) throws Exception {
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

    @NonNull
    private URI getUrl() throws URISyntaxException {
        if (baseUrl.endsWith("/")) {
            return new URI(baseUrl + "api/benchmark");
        } else {
            return new URI(baseUrl + "/api/benchmark");
        }
    }

}
