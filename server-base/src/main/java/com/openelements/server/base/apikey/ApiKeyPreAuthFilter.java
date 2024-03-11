package com.openelements.server.base.apikey;

import edu.umd.cs.findbugs.annotations.NonNull;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Objects;
import java.util.Optional;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

//Based on https://howtodoinjava.com/spring-security/custom-token-auth-example/
public class ApiKeyPreAuthFilter extends AbstractPreAuthenticatedProcessingFilter {

    private final DefaultApiKeyService apiKeyService;

    public ApiKeyPreAuthFilter(@NonNull final DefaultApiKeyService apiKeyService) {
        this.apiKeyService = Objects.requireNonNull(apiKeyService, "apiKeyService must not be null");
    }

    @Override
    protected Object getPreAuthenticatedPrincipal(@NonNull final HttpServletRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        final String user = extractUser(request);
        final String key = extractApiKey(request);
        return getPreAuthenticatedPrincipal(user, key);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(@NonNull final HttpServletRequest request) {
        Objects.requireNonNull(request, "request must not be null");
        final String user = extractUser(request);
        final String key = extractApiKey(request);
        return getPreAuthenticatedCredentials(user, key);
    }

    private Object getPreAuthenticatedCredentials(String user, String key) {
        apiKeyService.check(user, key);
        throw new IllegalStateException("Not yet implemented");
    }

    private Object getPreAuthenticatedPrincipal(@NonNull final String user, @NonNull final String key) {
        apiKeyService.check(user, key);
        throw new IllegalStateException("Not yet implemented");
    }

    private String extractApiKey(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("benchscape-api-key"))
                .orElseThrow(() -> new IllegalArgumentException("benchscape-api-key header is missing"));
    }

    private String extractUser(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader("benchscape-api-user"))
                .orElseThrow(() -> new IllegalArgumentException("benchscape-api-user header is missing"));
    }
}
