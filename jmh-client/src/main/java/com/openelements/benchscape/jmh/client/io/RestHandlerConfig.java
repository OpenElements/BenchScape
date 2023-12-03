package com.openelements.benchscape.jmh.client.io;

public class RestHandlerConfig {

    private String baseUrl;

    private String apiPrincipal;

    private String apiKey;

    public RestHandlerConfig() {
    }

    public RestHandlerConfig(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public RestHandlerConfig(String baseUrl, String apiPrincipal, String apiKey) {
        this.baseUrl = baseUrl;
        this.apiPrincipal = apiPrincipal;
        this.apiKey = apiKey;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getApiPrincipal() {
        return apiPrincipal;
    }

    public void setApiPrincipal(String apiPrincipal) {
        this.apiPrincipal = apiPrincipal;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
