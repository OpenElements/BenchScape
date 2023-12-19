package com.openelements.benchscape.jmh.client.io;

import com.openelements.benchscape.jmh.client.json.BenchmarkJsonFactory;
import com.openelements.benchscape.jmh.model.BenchmarkExecution;
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
public class RestHandler {

    public static final String CONTENT_TYPE_HEADER_NAME = "Content-Type";
    public static final String BENCHSCAPE_API_PRINCIPAL_HEADER_NAME = "Benchscape-Api-Principal";

    public static final String BENCHSCAPE_API_KEY_HEADER_NAME = "Benchscape-Api-Key";

    public static final String CONTENT_TYPE_HEADER_VALUE = "application/json";
    private final String baseUrl;

    private final String apiPrincipal;

    private final String apiKey;

    /**
     * Creates a new instance.
     *
     * @param config the config to call the endpoint
     */
    public RestHandler(@NonNull RestHandlerConfig config) {
        Objects.requireNonNull(config, "config must not be null");
        this.baseUrl = config.getBaseUrl();
        this.apiPrincipal = config.getApiPrincipal();
        this.apiKey = config.getApiKey();
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

        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(getUrl())
                .POST(bodyPublisher)
                .header(CONTENT_TYPE_HEADER_NAME, CONTENT_TYPE_HEADER_VALUE);
        if (apiPrincipal != null) {
            builder = builder.header(BENCHSCAPE_API_PRINCIPAL_HEADER_NAME, apiPrincipal);
        }
        if (apiKey != null) {
            builder = builder.header(BENCHSCAPE_API_KEY_HEADER_NAME, apiKey);
        }
        final HttpRequest request = builder.build();

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
            return new URI(baseUrl + "api/v2/execution");
        } else {
            return new URI(baseUrl + "/api/v2/execution");
        }
    }

}
