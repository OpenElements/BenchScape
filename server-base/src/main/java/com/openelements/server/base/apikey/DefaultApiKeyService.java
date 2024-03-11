package com.openelements.server.base.apikey;

import com.openelements.server.base.auth.UserService;
import com.openelements.server.base.tenant.TenantService;
import edu.umd.cs.findbugs.annotations.NonNull;
import io.micrometer.observation.annotation.Observed;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultApiKeyService implements ApiKeyService {

    public static final String SHA_256 = "SHA-256";

    private final KeyEntityRepository repository;

    private final TenantService tenantService;

    private final UserService userService;

    @Autowired
    public DefaultApiKeyService(@NonNull final KeyEntityRepository repository,
            @NonNull final TenantService tenantService,
            @NonNull final UserService userService) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.tenantService = Objects.requireNonNull(tenantService, "tenantService must not be null");
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
    }

    @NonNull
    public ApiKey createKey() {
        return createKey(LocalDateTime.now().plusYears(10));
    }

    @NonNull
    @Override
    public ApiKey createKey(@NonNull final LocalDateTime validUntil) {
        Objects.requireNonNull(validUntil, "validUntil must not be null");
        final String tenant = tenantService.getCurrentTenant();
        final String user = userService.getCurrentUser().mailAddress();
        final String key = UUID.randomUUID().toString();

        final ApiKeyEntity apiKeyEntity = new ApiKeyEntity();
        apiKeyEntity.setTenantId(tenant);
        apiKeyEntity.setUser(user);
        apiKeyEntity.setKeyHash(createHash(key));
        apiKeyEntity.setValidUntil(validUntil);
        final ApiKeyEntity savedEntity = repository.save(apiKeyEntity);

        final ApiKey freshApiKey = new ApiKey(savedEntity.id(), tenant, user, key, validUntil);
        return freshApiKey;
    }

    @Override
    public void check(@NonNull final String user, @NonNull final String key) {
        Objects.requireNonNull(user, "user must not be null");
        Objects.requireNonNull(key, "key must not be null");

        final String hash = createHash(key);
        final ApiKeyEntity apiKey = repository.findByUserAndHash(user, hash)
                .orElseThrow(() -> new IllegalArgumentException("Invalid key"));
        Optional.ofNullable(apiKey.getValidUntil()).ifPresent(validUntil -> {
            if (LocalDateTime.now().isAfter(validUntil)) {
                throw new IllegalArgumentException("Key expired");
            }
        });
    }

    @NonNull
    private String createHash(@NonNull final String key) {
        try {
            final MessageDigest digest = MessageDigest.getInstance(SHA_256);
            final byte[] encodedhash = digest.digest(key.getBytes(StandardCharsets.UTF_8));
            return new String(encodedhash, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to create hash", e);
        }
    }

}
