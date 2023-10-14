package com.openelements.jmh.store.v2.services;

import com.openelements.jmh.store.v2.data.Environment;
import com.openelements.jmh.store.v2.data.MeasurementMetadata;
import com.openelements.jmh.store.v2.entities.EnvironmentEntity;
import com.openelements.jmh.store.v2.repositories.EnvironmentRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EnvironmentService {

    private final EnvironmentRepository repository;

    @Autowired
    public EnvironmentService(final @NonNull EnvironmentRepository repository) {
        this.repository = Objects.requireNonNull(repository, "repository must not be null");
    }

    @NonNull
    public Optional<Environment> get(@NonNull final UUID id) {
        Objects.requireNonNull(id, "id must not be null");
        return repository.findById(id)
                .map(EnvironmentService::map);
    }

    @NonNull
    private static Environment map(@NonNull final EnvironmentEntity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return new Environment(entity.getId(), entity.getGitOriginUrl(),
                entity.getGitBranch(), entity.getSystemArch(),
                entity.getSystemProcessors(), entity.getSystemProcessorsMin(),
                entity.getSystemProcessorsMax(), entity.getSystemMemory(),
                entity.getSystemMemoryMin(), entity.getSystemMemoryMax(),
                entity.getOsName(), entity.getOsVersion(),
                entity.getJvmVersion(), entity.getJvmName(),
                entity.getJmhVersion());
    }

    @NonNull
    private static EnvironmentEntity mapToEntity(final @NonNull Environment environment) {
        Objects.requireNonNull(environment, "environment must not be null");
        final EnvironmentEntity entity = new EnvironmentEntity();
        entity.setId(environment.id());
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

    public boolean isMatchingEnvironment(@NonNull final MeasurementMetadata metadata,
            @NonNull final String environmentId) {
        Objects.requireNonNull(environmentId, "environmentId must not be null");
        return isMatchingEnvironment(metadata, UUID.fromString(environmentId));
    }

    public boolean isMatchingEnvironment(@NonNull final MeasurementMetadata metadata,
            @NonNull final UUID environmentId) {
        Objects.requireNonNull(environmentId, "environmentId must not be null");
        final EnvironmentEntity environmentEntity = repository.findById(environmentId)
                .orElseThrow(() -> new IllegalArgumentException("No environment found for ID: " + environmentId));
        final Environment environment = map(environmentEntity);
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
    public List<Environment> getAll() {
        return repository.findAll().stream()
                .map(EnvironmentService::map)
                .toList();
    }

    @NonNull
    public Environment find(String id) {
        return repository.findById(UUID.fromString(id))
                .map(EnvironmentService::map)
                .orElseThrow(() -> new IllegalArgumentException("No environment found for ID: " + id));
    }

    @NonNull
    public Environment save(final @NonNull Environment environment) {
        return map(repository.save(mapToEntity(environment)));
    }

    public void delete(@NonNull String id) {
        Objects.requireNonNull(id, "id must not be null");
        repository.deleteById(UUID.fromString(id));
    }
}
