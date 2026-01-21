package com.openelements.benchscape.server.store.repositories;

import com.openelements.benchscape.server.store.data.OperationSystem;
import com.openelements.benchscape.server.store.data.SystemMemory;
import com.openelements.benchscape.server.store.entities.EnvironmentEntity;
import com.openelements.benchscape.server.store.entities.EnvironmentEntity_;
import com.openelements.server.base.tenantdata.EntityWithTenantRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface EnvironmentRepository extends EntityWithTenantRepository<EnvironmentEntity>,
        JpaSpecificationExecutor<EnvironmentEntity> {

    default List<EnvironmentEntity> findFilteredEnvironments(String name, String gitOriginUrl, String gitBranch, String systemArch,
                                                             Integer systemProcessors, Integer systemProcessorsMin,
                                                             Integer systemProcessorsMax, SystemMemory systemMemory,
                                                             SystemMemory systemMemoryMin, SystemMemory systemMemoryMax,
                                                             OperationSystem osFamily, String osName, String osVersion, String jvmVersion,
                                                             String jvmName, String jmhVersion) {
        return findAll(createSpecificationForQuery(name, gitOriginUrl, gitBranch, systemArch,
                systemProcessors, systemProcessorsMin, systemProcessorsMax, systemMemory, systemMemoryMin, systemMemoryMax,
                osFamily, osName, osVersion, jvmVersion, jvmName, jmhVersion));
    }

    private static Specification<EnvironmentEntity> createSpecificationForQuery(String name, String gitOriginUrl, String gitBranch,
                                                                                String systemArch, Integer systemProcessors,
                                                                                Integer systemProcessorsMin, Integer systemProcessorsMax,
                                                                                SystemMemory systemMemory, SystemMemory systemMemoryMin,
                                                                                SystemMemory systemMemoryMax, OperationSystem osFamily,
                                                                                String osName, String osVersion, String jvmVersion,
                                                                                String jvmName, String jmhVersion) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EnvironmentEntity_.name)), "%" +
                        name.toLowerCase() + "%"));
            }
            if (gitOriginUrl != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EnvironmentEntity_.gitOriginUrl)), "%" +
                        gitOriginUrl.toLowerCase() + "%"));
            }
            if (gitBranch != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(EnvironmentEntity_.gitBranch)),
                        gitBranch.toLowerCase()));
            }
            if (systemArch != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(EnvironmentEntity_.systemArch)),
                        systemArch.toLowerCase()));
            }
            if (systemProcessors != null) {
                predicates.add(criteriaBuilder.equal(root.get(EnvironmentEntity_.systemProcessors), systemProcessors));
            }
            if (systemProcessorsMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(EnvironmentEntity_.systemProcessors), systemProcessorsMin));
            }
            if (systemProcessorsMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(EnvironmentEntity_.systemProcessors), systemProcessorsMax));
            }
            if (systemMemory != null) {
                predicates.add(criteriaBuilder.equal(root.get(EnvironmentEntity_.systemMemory).get("type"), systemMemory.toBytes()));
            }

            if (systemMemoryMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(EnvironmentEntity_.systemMemory).get("value"), systemMemoryMin.toBytes()));
            }

            if (systemMemoryMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(EnvironmentEntity_.systemMemory).get("value"), systemMemoryMax.toBytes()));
            }
            if (osFamily != null) {
                predicates.add(criteriaBuilder.equal(root.get(EnvironmentEntity_.osFamily), osFamily));
            }
            if (osName != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EnvironmentEntity_.osName)), "%" +
                        osName.toLowerCase() + "%"));
            }
            if (osVersion != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EnvironmentEntity_.osVersion)), "%" +
                        osVersion.toLowerCase() + "%"));
            }
            if (jvmVersion != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EnvironmentEntity_.jvmVersion)), "%" +
                        jvmVersion.toLowerCase() + "%"));
            }
            if (jvmName != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EnvironmentEntity_.jvmName)), "%" +
                        jvmName.toLowerCase() + "%"));
            }
            if (jmhVersion != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(EnvironmentEntity_.jmhVersion)), "%" +
                        jmhVersion.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
