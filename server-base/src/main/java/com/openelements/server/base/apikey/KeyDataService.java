package com.openelements.server.base.apikey;

import com.openelements.server.base.tenant.TenantService;
import com.openelements.server.base.tenantdata.AbstractServiceWithTenant;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class KeyDataService extends AbstractServiceWithTenant<ApiKeyEntity, ApiKeyData> {

    private final KeyEntityRepository repository;

    private final TenantService tenantService;

    private final UserService userService;

    @Autowired
    public KeyDataService(@NonNull final KeyEntityRepository repository, @NonNull final TenantService tenantService,
            @NonNull final UserService userService) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.tenantService = Objects.requireNonNull(tenantService, "tenantService must not be null");
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
    }

    @Override
    protected KeyEntityRepository getRepository() {
        return repository;
    }

    @Override
    protected TenantService getTenantService() {
        return tenantService;
    }

    @Override
    protected ApiKeyData mapToData(ApiKeyEntity entity) {
        return new ApiKeyData(entity.getId(), entity.getName(), entity.getUser(), entity.getKey(),
                entity.getValidUntil());
    }

    @Override
    protected ApiKeyEntity updateEntity(ApiKeyEntity entity, ApiKeyData data) {
        entity.setName(data.name());
        entity.setUser(data.user());
        entity.setKey(data.key());
        entity.setValidUntil(data.validUntil());
        return entity;
    }

    @Override
    protected ApiKeyEntity createNewEntity() {
        return new ApiKeyEntity();
    }

    @NonNull
    public ApiKeyData createNewKey(@NonNull final String name) {
        return createNewKey(name, LocalDateTime.now().plusYears(100));
    }

    @NonNull
    public ApiKeyData createNewKey(final @NonNull String name, final @NonNull LocalDateTime validUntil) {
        Objects.requireNonNull(validUntil, "validUntil must not be null");
        Objects.requireNonNull(name, "name must not be null");
        if (name.isBlank()) {
            throw new IllegalArgumentException("name must not be blank");
        }
        ApiKeyData data = new ApiKeyData(null, name, getCurrentUser(), UUID.randomUUID().toString(), null);
        return save(data);
    }

    public void check(@NonNull final String user, @NonNull final String key) {
        Objects.requireNonNull(user, "user must not be null");
        Objects.requireNonNull(key, "key must not be null");
        if (user.isBlank()) {
            throw new IllegalArgumentException("User must not be blank");
        }
        if (key.isBlank()) {
            throw new IllegalArgumentException("Key must not be blank");
        }
        final List<ApiKeyEntity> collect = getRepository().findAllByTenantId(getCurrentTenantId()).stream()
                .filter(e -> Objects.equals(e.getUser(), user))
                .filter(e -> Objects.equals(e.getKey(), key))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            throw new IllegalArgumentException("No key found for user: " + user + " and key: " + key);
        }
        collect.stream()
                .filter(e -> e.getValidUntil().isAfter(LocalDateTime.now()))
                .findAny()
                .orElseThrow(() -> new IllegalArgumentException(
                        "Key expired for user: " + user + " and key: " + key));
    }

    public void check(@NonNull final ApiKey apiKey) {
        Objects.requireNonNull(apiKey, "apiKey must not be null");
        check(apiKey.user(), apiKey.key());
    }

    @NonNull
    private String getCurrentUser() {
        final String user = userService.getCurrentUser();
        if (user == null) {
            throw new IllegalStateException("No user set");
        }
        if (user.isBlank()) {
            throw new IllegalArgumentException("User must not be blank");
        }
        return user;
    }
}
