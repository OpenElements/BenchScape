package com.openelements.benchscape.server.store.services;

import com.openelements.benchscape.server.store.data.Environment;
import com.openelements.benchscape.server.store.data.EnvironmentQuery;
import com.openelements.benchscape.server.store.data.MeasurementMetadata;
import com.openelements.benchscape.server.store.entities.EnvironmentEntity;
import com.openelements.benchscape.server.store.repositories.EnvironmentRepository;
import com.openelements.server.base.tenant.TenantService;
import com.openelements.server.base.tenantdata.AbstractServiceWithTenant;
import edu.umd.cs.findbugs.annotations.NonNull;
import edu.umd.cs.findbugs.annotations.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.io.FileUtils;
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
                Optional.ofNullable(entity.getSystemMemory())
                        .map(m -> FileUtils.byteCountToDisplaySize(m))
                        .orElse(null),
                Optional.ofNullable(entity.getSystemMemoryMin())
                        .map(m -> FileUtils.byteCountToDisplaySize(m))
                        .orElse(null),
                Optional.ofNullable(entity.getSystemMemoryMax())
                        .map(m -> FileUtils.byteCountToDisplaySize(m))
                        .orElse(null),
                entity.getOsName(), entity.getOsVersion(), entity.getOsFamily(),
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
                .map(systemMemoryMin -> metadata.systemMemory() != null &&
                        systemMemoryMin <= metadata.systemMemory())
                .orElse(true);
        if (!systemMemoryMinCheck) {
            return false;
        }

        final Boolean systemMemoryMaxCheck = Optional.ofNullable(environment.systemMemoryMax())
                .map(systemMemoryMax -> metadata.systemMemory() != null &&
                        systemMemoryMax >= metadata.systemMemory())
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

//        final Boolean osFamilyCheck = Optional.ofNullable(environment.osFamily())
//                .map(osFamily -> stringMetadataValueMatches(osFamily, metadata.OsFamily(), false))
//                .orElse(true);
//        if (!osFamilyCheck) {
//            return false;
//        }

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
        if (!jmhVersionCheck) {
            return false;
        }


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

    public List<Environment> findByQuery(@NonNull final EnvironmentQuery environmentQuery) {
        Objects.requireNonNull(environmentQuery, "environmentQuery must not be null");
        return repository.findAllByQuery(environmentQuery).stream()
                .map(this::mapToData)
                .toList();
    }
}