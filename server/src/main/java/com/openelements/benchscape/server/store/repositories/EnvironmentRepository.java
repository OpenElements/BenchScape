package com.openelements.benchscape.server.store.repositories;

import com.openelements.benchscape.server.store.data.EnvironmentQuery;
import com.openelements.benchscape.server.store.data.OperationSystem;
import com.openelements.benchscape.server.store.data.SystemMemory;
import com.openelements.benchscape.server.store.entities.EnvironmentEntity;
import com.openelements.server.base.tenantdata.EntityWithTenantRepository;
import edu.umd.cs.findbugs.annotations.NonNull;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentRepository extends EntityWithTenantRepository<EnvironmentEntity>,
        JpaSpecificationExecutor<EnvironmentEntity> {

    default List<EnvironmentEntity> findFilteredEnvironments(String name, String gitOriginUrl, String gitBranch, String systemArch,
                                                             Integer systemProcessors, Integer systemProcessorsMin, Integer systemProcessorsMax,
                                                             SystemMemory systemMemory, SystemMemory systemMemoryMin, SystemMemory systemMemoryMax,
                                                             OperationSystem osFamily, String osName, String osVersion, String jvmVersion, String jvmName,
                                                             String jmhVersion) {
        return findAll(createSpecificationForQuery(name, gitOriginUrl, gitBranch, systemArch,
                systemProcessors, systemProcessorsMin, systemProcessorsMax, systemMemory, systemMemoryMin, systemMemoryMax,
                osFamily, osName, osVersion, jvmVersion, jvmName, jmhVersion));
    }

    private static Specification<EnvironmentEntity> createSpecificationForQuery(String name, String gitOriginUrl, String gitBranch, String systemArch,
                                                                                Integer systemProcessors, Integer systemProcessorsMin, Integer systemProcessorsMax,
                                                                                SystemMemory systemMemory, SystemMemory systemMemoryMin, SystemMemory systemMemoryMax,
                                                                                OperationSystem osFamily, String osName, String osVersion, String jvmVersion, String jvmName,
                                                                                String jmhVersion) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            final String SYSTEM_ARCH_QUERY_FIELD = "systemArch";
            final String GIT_ORIGIN_URL_QUERY_FIELD = "gitOriginUrl";
            final String GIT_BRANCH_QUERY_FIELD = "gitBranch";
            final String SYSTEM_PROCESSORS_QUERY_FIELD = "systemProcessors";
            final String SYSTEM_PROCESSORS_MIN_QUERY_FIELD = "systemProcessorsMin";
            final String SYSTEM_PROCESSORS_MAX_QUERY_FIELD = "systemProcessorsMax";
            final String SYSTEM_MEMORY_QUERY_FIELD = "systemMemory";
            final String SYSTEM_MEMORY_MIN_QUERY_FIELD = "systemMemoryMin";
            final String SYSTEM_MEMORY_MAX_QUERY_FIELD = "systemMemoryMax";
            final String OS_NAME_QUERY_FIELD = "osName";
            final String OS_VERSION_QUERY_FIELD = "osVersion";
            final String JVM_VERSION_QUERY_FIELD = "jvmVersion";
            final String JVM_NAME_QUERY_FIELD = "jvmName";
            final String OS_FAMILY_QUERY_FIELD = "osFamily";
            final String JMH_VERSION_QUERY_FIELD = "jmhVersion";

            if (name != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
            }
            if (gitOriginUrl != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(GIT_ORIGIN_URL_QUERY_FIELD)), "%" + gitOriginUrl.toLowerCase() + "%"));
            }
            if (gitBranch != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(GIT_BRANCH_QUERY_FIELD)), gitBranch.toLowerCase()));
            }
            if (systemArch != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(SYSTEM_ARCH_QUERY_FIELD)), systemArch.toLowerCase()));
            }
            if (systemProcessors != null) {
                predicates.add(criteriaBuilder.equal(root.get(SYSTEM_PROCESSORS_QUERY_FIELD), systemProcessors));
            }
            if (systemProcessorsMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(SYSTEM_PROCESSORS_MIN_QUERY_FIELD), systemProcessorsMin));
            }
            if (systemProcessorsMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(SYSTEM_PROCESSORS_MAX_QUERY_FIELD), systemProcessorsMax));
            }
            if (systemMemory != null) {
                predicates.add(criteriaBuilder.equal(root.get(SYSTEM_MEMORY_QUERY_FIELD), SystemMemory.getToByteConverter().apply(systemMemory)));
            }
            if (systemMemoryMin != null) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(SYSTEM_MEMORY_MIN_QUERY_FIELD), SystemMemory.getToByteConverter().apply(systemMemoryMin)));
            }
            if (systemMemoryMax != null) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(SYSTEM_MEMORY_MAX_QUERY_FIELD), SystemMemory.getToByteConverter().apply(systemMemoryMax)));
            }
            if (osFamily != null) {
                predicates.add(criteriaBuilder.equal(criteriaBuilder.lower(root.get(OS_FAMILY_QUERY_FIELD)), osFamily.toString().toLowerCase()));
            }
            if (osName != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(OS_NAME_QUERY_FIELD)), "%" + osName.toLowerCase() + "%"));
            }
            if (osVersion != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(OS_VERSION_QUERY_FIELD)), "%" + osVersion.toLowerCase() + "%"));
            }
            if (jvmVersion != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(JVM_VERSION_QUERY_FIELD)), "%" + jvmVersion.toLowerCase() + "%"));
            }
            if (jvmName != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(JVM_NAME_QUERY_FIELD)), "%" + jvmName.toLowerCase() + "%"));
            }
            if (jmhVersion != null) {
                predicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(JMH_VERSION_QUERY_FIELD)), "%" + jmhVersion.toLowerCase() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
