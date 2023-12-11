package com.openelements.benchscape.server.store.services;

import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.data.MeasurementMetadata;
import com.openelements.benchscape.server.store.entities.EnvironmentEntity;
import com.openelements.benchscape.server.store.repositories.EnvironmentRepository;
import com.openelements.server.base.tenant.TenantService;
import com.openelements.server.base.tenantdata.AbstractServiceWithTenant;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EnvironmentService extends AbstractServiceWithTenant<EnvironmentEntity, Environment> {

    private final EnvironmentRepository repository;

    private final TenantService tenantService;

    @Autowired
    public EnvironmentService(final @NonNull EnvironmentRepository repository, TenantService tenantService) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.tenantService = Objects.requireNonNull(tenantService, "tenantService must not be null");
    }

    @Override
    protected TenantService getTenantService() {
        return tenantService;
    }

    @NonNull
    @Override
    protected EnvironmentRepository getRepository() {
        return repository;
    }

    @NonNull
    @Override
    protected Environment mapToData(@NonNull EnvironmentEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Environment(entity.getId(), entity.getName(), entity.getDescription(), entity.getGitOriginUrl(),
                entity.getGitBranch(), entity.getSystemArch(),
                entity.getSystemProcessors(), entity.getSystemProcessorsMin(),
                entity.getSystemProcessorsMax(), entity.getSystemMemory(),
                entity.getSystemMemoryMin(), entity.getSystemMemoryMax(),
                entity.getOsName(), entity.getOsVersion(),
                entity.getJvmVersion(), entity.getJvmName(),
                entity.getJmhVersion());
    }

    @NonNull
    @Override
    protected EnvironmentEntity updateEntity(@NonNull EnvironmentEntity entity,
            @NonNull Environment environment) {
        entity.setId(environment.id());
        entity.setName(environment.name());
        entity.setDescription(environment.description());
        entity.setGitOriginUrl(environment.gitOriginUrl());
        entity.setGitBranch(environment.gitBranch());
        entity.setSystemArch(environment.systemArch());
        entity.setSystemProcessors(environment.systemProcessors());
        entity.setSystemProcessorsMin(environment.systemProcessorsMin());
        entity.setSystemProcessorsMax(environment.systemProcessorsMax());
        entity.setSystemMemory(environment.systemMemory());
        entity.setSystemMemoryMin(environment.systemMemoryMin());
        entity.setSystemMemoryMax(environment.systemMemoryMax());
        entity.setOsName(environment.osName());
        entity.setOsVersion(environment.osVersion());
        entity.setJvmVersion(environment.jvmVersion());
        entity.setJvmName(environment.jvmName());
        entity.setJmhVersion(environment.jmhVersion());
        return entity;
    }

    @NonNull
    @Override
    protected EnvironmentEntity createNewEntity() {
        return new EnvironmentEntity();
    }

    public boolean isMatchingEnvironment(@NonNull final MeasurementMetadata metadata,
            @NonNull final String environmentId) {
        Objects.requireNonNull(environmentId, "environmentId must not be null");
        return isMatchingEnvironment(metadata, UUID.fromString(environmentId));
    }

    public boolean isMatchingEnvironment(@NonNull final MeasurementMetadata metadata,
            @NonNull final UUID environmentId) {
        Objects.requireNonNull(environmentId, "environmentId must not be null");
        final EnvironmentEntity environmentEntity = repository.findByIdAndTenantId(environmentId, getCurrentTenantId())
                .orElseThrow(() -> new IllegalArgumentException("No environment found for ID: " + environmentId));
        final Environment environment = mapToData(environmentEntity);
        return isMatchingEnvironment(metadata, environment);
    }

    public boolean isMatchingEnvironment(@NonNull final MeasurementMetadata metadata,
            @NonNull final Environment environment) {
        Objects.requireNonNull(metadata, "metadata must not be null");
        Objects.requireNonNull(environment, "environment must not be null");

        final boolean gitOriginUrlCheck = Optional.ofNullable(environment.gitOriginUrl())
                .map(gitUrl -> stringMetadataValueMatches(gitUrl, metadata.gitOriginUrl(), false))
                .orElse(true);
        if (gitOriginUrlCheck == false) {
            return false;
        }

        final Boolean gitBranchCheck = Optional.ofNullable(environment.gitBranch())
                .map(gitBranch -> stringMetadataValueMatches(gitBranch, metadata.gitBranch(), false))
                .orElse(true);
        if (gitBranchCheck == false) {
            return false;
        }

        final Boolean systemArchCheck = Optional.ofNullable(environment.systemArch())
                .map(systemArch -> stringMetadataValueMatches(systemArch, metadata.systemArch(), false))
                .orElse(true);
        if (systemArchCheck == false) {
            return false;
        }

        final Boolean systemProcessorsCheck = Optional.ofNullable(environment.systemProcessors())
                .map(systemProcessors -> {
                    if (metadata.systemProcessors() == null) {
                        return false;
                    }
                    return systemProcessors == metadata.systemProcessors();
                })
                .orElse(true);
        if (systemProcessorsCheck == false) {
            return false;
        }

        final Boolean systemProcessorsMinCheck = Optional.ofNullable(environment.systemProcessorsMin())
                .map(systemProcessorsMin -> {
                    if (metadata.systemProcessors() == null) {
                        return false;
                    }
                    return systemProcessorsMin <= metadata.systemProcessors();
                })
                .orElse(true);
        if (systemProcessorsMinCheck == false) {
            return false;
        }

        //TODO: all other checks

        return true;
    }

    private boolean stringMetadataValueMatches(@NonNull final String environmentPattern,
            @Nullable final String metadataValue, final boolean caseSensitive) {
        Objects.requireNonNull(environmentPattern, "environmentPattern must not be null");
        if (environmentPattern.isBlank()) {
            return true;
        }
        if (metadataValue == null) {
            return false;
        }
        if (!caseSensitive && Objects.equals(environmentPattern.toLowerCase(), metadataValue.toLowerCase())) {
            return true;
        }
        if (caseSensitive && Objects.equals(environmentPattern, metadataValue)) {
            return true;
        }
        if (caseSensitive) {
            final String pattern = environmentPattern.replace("*", ".*");
            return metadataValue.matches(pattern);
        } else {
            final String pattern = environmentPattern.toLowerCase().replace("*", ".*");
            return metadataValue.toLowerCase().matches(pattern);
        }
    }
}
