package com.openelements.benchscape.server.store.repositories;

import com.openelements.benchscape.server.store.data.EnvironmentQuery;
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

    default List<EnvironmentEntity> findAllByQuery(@NonNull final EnvironmentQuery environmentQuery) {
        return findAll(createSpecificationForQuery(environmentQuery));
    }

    private static Specification<EnvironmentEntity> createSpecificationForQuery(@NonNull final EnvironmentQuery environmentQuery) {
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

            if (environmentQuery.systemArch() != null) {
                predicates.add(criteriaBuilder.equal(root.get(SYSTEM_ARCH_QUERY_FIELD), environmentQuery.systemArch()));
            }
            if (environmentQuery.gitOriginUrl() != null) {
                predicates.add(criteriaBuilder.equal(root.get(GIT_ORIGIN_URL_QUERY_FIELD), environmentQuery.gitOriginUrl()));
            }
            if (environmentQuery.gitBranch() != null) {
                predicates.add(criteriaBuilder.equal(root.get(GIT_BRANCH_QUERY_FIELD), environmentQuery.gitBranch()));
            }
            if (environmentQuery.systemProcessors() != null) {
                predicates.add(criteriaBuilder.equal(root.get(SYSTEM_PROCESSORS_QUERY_FIELD), environmentQuery.systemProcessors()));
            }
            if (environmentQuery.systemProcessorsMin() != null) {
                predicates.add(criteriaBuilder.equal(root.get(SYSTEM_PROCESSORS_MIN_QUERY_FIELD), environmentQuery.systemProcessorsMin()));
            }
            if (environmentQuery.systemProcessorsMax() != null) {
                predicates.add(criteriaBuilder.equal(root.get(SYSTEM_PROCESSORS_MAX_QUERY_FIELD), environmentQuery.systemProcessorsMax()));
            }
            if (environmentQuery.systemMemory() != null) {
                predicates.add(criteriaBuilder.equal(root.get(SYSTEM_MEMORY_QUERY_FIELD), environmentQuery.systemMemory().toBytes()));
            }
            if (environmentQuery.systemMemoryMin() != null) {
                predicates.add(criteriaBuilder.equal(root.get(SYSTEM_MEMORY_MIN_QUERY_FIELD), environmentQuery.systemMemoryMin().toBytes()));
            }
            if (environmentQuery.systemMemoryMax() != null) {
                predicates.add(criteriaBuilder.equal(root.get(SYSTEM_MEMORY_MAX_QUERY_FIELD), environmentQuery.systemMemoryMax().toBytes()));
            }
            if (environmentQuery.osName() != null) {
                predicates.add(criteriaBuilder.equal(root.get(OS_NAME_QUERY_FIELD), environmentQuery.osName()));
            }
            if (environmentQuery.osVersion() != null) {
                predicates.add(criteriaBuilder.equal(root.get(OS_VERSION_QUERY_FIELD), environmentQuery.osVersion()));
            }
            if (environmentQuery.jvmVersion() != null) {
                predicates.add(criteriaBuilder.equal(root.get(JVM_VERSION_QUERY_FIELD), environmentQuery.jvmVersion()));
            }
            if (environmentQuery.jvmName() != null) {
                predicates.add(criteriaBuilder.equal(root.get(JVM_NAME_QUERY_FIELD), environmentQuery.jvmName()));
            }
            if (environmentQuery.osFamily() != null) {
                predicates.add(criteriaBuilder.equal(root.get(OS_FAMILY_QUERY_FIELD), environmentQuery.osFamily()));
            }
            if (environmentQuery.jmhVersion() != null) {
                predicates.add(criteriaBuilder.equal(root.get(JMH_VERSION_QUERY_FIELD), environmentQuery.jmhVersion()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
