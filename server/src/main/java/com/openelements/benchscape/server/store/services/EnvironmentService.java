package com.openelements.benchscape.server.store.services;

import com.openelements.benchscape.server.store.data.*;
import com.openelements.benchscape.server.store.entities.EnvironmentEntity;
import com.openelements.benchscape.server.store.repositories.EnvironmentRepository;
import com.openelements.server.base.tenant.TenantService;
import com.openelements.server.base.tenantdata.AbstractServiceWithTenant;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EnvironmentService extends AbstractServiceWithTenant<EnvironmentEntity, Environment> {

    private final EnvironmentRepository repository;

    private final TenantService tenantService;

    @Autowired
    public EnvironmentService(final @NonNull EnvironmentRepository repository,
            final @NonNull TenantService tenantService) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
        this.tenantService = Objects.requireNonNull(tenantService, "tenantService must not be null");
    }

    @Override
    @NonNull
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
    protected Environment mapToData(@NonNull final EnvironmentEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Environment(entity.getId(), entity.getName(), entity.getDescription(), entity.getGitOriginUrl(),
                entity.getGitBranch(), entity.getSystemArch(),
                entity.getSystemProcessors(), entity.getSystemProcessorsMin(),
                entity.getSystemProcessorsMax(), SystemMemory.getFromByteConverter().apply(entity.getSystemMemory()),
                SystemMemory.getFromByteConverter().apply(entity.getSystemMemoryMin()),
                SystemMemory.getFromByteConverter().apply(entity.getSystemMemoryMax()),
                entity.getOsName(), entity.getOsVersion(), entity.getOsFamily(),
                entity.getJvmVersion(), entity.getJvmName(),
                entity.getJmhVersion());
    }

    @NonNull
    @Override
    protected EnvironmentEntity updateEntity(@NonNull final EnvironmentEntity entity,
                                             @NonNull final Environment environment) {
        entity.setId(environment.id());
        entity.setName(environment.name());
        entity.setDescription(environment.description());
        entity.setGitOriginUrl(environment.gitOriginUrl());
        entity.setGitBranch(environment.gitBranch());
        entity.setSystemArch(environment.systemArch());
        entity.setSystemProcessors(environment.systemProcessors());
        entity.setSystemProcessorsMin(environment.systemProcessorsMin());
        entity.setSystemProcessorsMax(environment.systemProcessorsMax());
        entity.setSystemMemory(SystemMemory.getToByteConverter().apply(environment.systemMemory()));
        entity.setSystemMemoryMin(SystemMemory.getToByteConverter().apply(environment.systemMemoryMin()));
        entity.setSystemMemoryMax(SystemMemory.getToByteConverter().apply(environment.systemMemoryMax()));
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

    public List<Environment> getFilteredEnvironments(String name, String gitOriginUrl, String gitBranch, String systemArch,
                                                     Integer systemProcessors, Integer systemProcessorsMin, Integer systemProcessorsMax,
                                                     SystemMemory systemMemory, SystemMemory systemMemoryMin, SystemMemory systemMemoryMax,
                                                     OperationSystem osFamily, String osName, String osVersion, String jvmVersion,
                                                     String jvmName, String jmhVersion) {
        List<EnvironmentEntity> filteredEntities = repository.findFilteredEnvironments(name, gitOriginUrl, gitBranch, systemArch,
                systemProcessors, systemProcessorsMin, systemProcessorsMax, systemMemory, systemMemoryMin, systemMemoryMax, osFamily, osName, osVersion, jvmVersion, jvmName, jmhVersion);

        return filteredEntities.stream().map(this::mapToData).collect(Collectors.toList());
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

    public boolean isAnyMatchingEnvironment(@NonNull final Environment env,
            @NonNull final List<MeasurementMetadata> metadata) {
        Objects.requireNonNull(metadata, "metadata must not be null");
        return metadata.stream()
                .filter(m -> isMatchingEnvironment(m, env))
                .findAny()
                .isPresent();
    }

    public boolean isMatchingEnvironment(@NonNull final MeasurementMetadata metadata,
                                         @NonNull final Environment environment) {
        Objects.requireNonNull(metadata, "metadata must not be null");
        Objects.requireNonNull(environment, "environment must not be null");

        final boolean gitOriginUrlCheck = Optional.ofNullable(environment.gitOriginUrl())
                .map(gitUrl -> stringMetadataValueMatches(gitUrl, metadata.gitOriginUrl(), false))
                .orElse(true);
        if (!gitOriginUrlCheck) {
            return false;
        }

        final Boolean gitBranchCheck = Optional.ofNullable(environment.gitBranch())
                .map(gitBranch -> stringMetadataValueMatches(gitBranch, metadata.gitBranch(), false))
                .orElse(true);
        if (!gitBranchCheck) {
            return false;
        }

        final Boolean systemArchCheck = Optional.ofNullable(environment.systemArch())
                .map(systemArch -> stringMetadataValueMatches(systemArch, metadata.systemArch(), false))
                .orElse(true);
        if (!systemArchCheck) {
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
        if (!systemProcessorsCheck) {
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
        if (!systemProcessorsMinCheck) {
            return false;
        }

        final Boolean systemProcessorsMaxCheck = Optional.ofNullable(environment.systemProcessorsMax())
                .map(systemProcessorsMax -> metadata.systemProcessors() != null &&
                        systemProcessorsMax >= metadata.systemProcessors())
                .orElse(true);
        if (!systemProcessorsMaxCheck) {
            return false;
        }

        final Boolean systemMemoryCheck = Optional.ofNullable(environment.systemMemory())
                .map(systemMemory -> metadata.systemMemory() != null &&
                        systemMemory.equals(metadata.systemMemory()))
                .orElse(true);
        if (!systemMemoryCheck) {
            return false;
        }

        final Boolean systemMemoryMinCheck = Optional.ofNullable(environment.systemMemoryMin())
                .map(min -> metadata.systemMemory() != null && min.isLessThanOrEqual(metadata.systemMemory()))
                .orElse(true);
        if (!systemMemoryMinCheck) {
            return false;
        }

        final Boolean systemMemoryMaxCheck = Optional.ofNullable(environment.systemMemoryMax())
                .map(max -> metadata.systemMemory() != null && max.isGreaterThanOrEqual(metadata.systemMemory()))
                .orElse(true);
        if (!systemMemoryMaxCheck) {
            return false;
        }

        final Boolean osNameCheck = Optional.ofNullable(environment.osName())
                .map(osName -> stringMetadataValueMatches(osName, metadata.osName(), false))
                .orElse(true);
        if (!osNameCheck) {
            return false;
        }

        final Boolean osVersionCheck = Optional.ofNullable(environment.osVersion())
                .map(osVersion -> stringMetadataValueMatches(osVersion, metadata.osVersion(), false))
                .orElse(true);
        if (!osVersionCheck) {
            return false;
        }

        final Boolean jvmVersionCheck = Optional.ofNullable(environment.jvmVersion())
                .map(jvmVersion -> stringMetadataValueMatches(jvmVersion, metadata.jvmVersion(), false))
                .orElse(true);
        if (!jvmVersionCheck) {
            return false;
        }

        final Boolean jvmNameCheck = Optional.ofNullable(environment.jvmName())
                .map(jvmName -> stringMetadataValueMatches(jvmName, metadata.jvmName(), false))
                .orElse(true);
        if (!jvmNameCheck) {
            return false;
        }

        final Boolean jmhVersionCheck = Optional.ofNullable(environment.jmhVersion())
                .map(jmhVersion -> stringMetadataValueMatches(jmhVersion, metadata.jmhVersion(), false))
                .orElse(true);
        return jmhVersionCheck;
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

    @NonNull
    public List<Environment> findByQuery(@NonNull final EnvironmentQuery environmentQuery) {
        Objects.requireNonNull(environmentQuery, "environmentQuery must not be null");
        return repository.findFilteredEnvironments(environmentQuery.name(), environmentQuery.gitOriginUrl(), environmentQuery.gitBranch(),
                        environmentQuery.systemArch(), environmentQuery.systemProcessors(), environmentQuery.systemProcessorsMin(),
                        environmentQuery.systemProcessorsMax(), environmentQuery.systemMemory(), environmentQuery.systemMemoryMin(),
                        environmentQuery.systemMemoryMax(), environmentQuery.osFamily(), environmentQuery.osName(),
                        environmentQuery.osVersion(), environmentQuery.jvmVersion(), environmentQuery.jvmName(),
                        environmentQuery.jmhVersion()).stream()
                .map(this::mapToData)
                .collect(Collectors.toList());
    }

}