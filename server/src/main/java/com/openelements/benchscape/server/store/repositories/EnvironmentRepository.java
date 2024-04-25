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
            if (environmentQuery.systemArch() != null) {
                predicates.add(criteriaBuilder.equal(root.get("systemArch"), environmentQuery.systemArch()));
            }
            if (environmentQuery.gitOriginUrl() != null) {
                predicates.add(criteriaBuilder.equal(root.get("gitOriginUrl"), environmentQuery.gitOriginUrl()));
            }
            if (environmentQuery.gitBranch() != null) {
                predicates.add(criteriaBuilder.equal(root.get("gitBranch"), environmentQuery.gitBranch()));
            }
            if (environmentQuery.systemProcessors() != null) {
                predicates.add(criteriaBuilder.equal(root.get("systemProcessors"), environmentQuery.systemProcessors()));
            }
            if (environmentQuery.systemProcessorsMin() != null) {
                predicates.add(criteriaBuilder.equal(root.get("systemProcessorsMin"), environmentQuery.systemProcessorsMin()));
            }
            if (environmentQuery.systemProcessorsMax() != null) {
                predicates.add(criteriaBuilder.equal(root.get("systemProcessorsMax"), environmentQuery.systemProcessorsMax()));
            }
            if (environmentQuery.systemMemory() != null) {
                predicates.add(criteriaBuilder.equal(root.get("systemMemory"), environmentQuery.systemMemory()));
            }
            if (environmentQuery.systemMemoryMin() != null) {
                predicates.add(criteriaBuilder.equal(root.get("systemMemoryMin"), environmentQuery.systemMemoryMin()));
            }
            if (environmentQuery.systemMemoryMax() != null) {
                predicates.add(criteriaBuilder.equal(root.get("systemMemoryMax"), environmentQuery.systemMemoryMax()));
            }
            if (environmentQuery.osName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("osName"), environmentQuery.osName()));
            }
            if (environmentQuery.osVersion() != null) {
                predicates.add(criteriaBuilder.equal(root.get("osVersion"), environmentQuery.osVersion()));
            }
            if (environmentQuery.jvmVersion() != null) {
                predicates.add(criteriaBuilder.equal(root.get("jvmVersion"), environmentQuery.jvmVersion()));
            }
            if (environmentQuery.jvmName() != null) {
                predicates.add(criteriaBuilder.equal(root.get("jvmName"), environmentQuery.jvmName()));
            }
            if (environmentQuery.osFamily() != null) {
                predicates.add(criteriaBuilder.equal(root.get("osFamily"), environmentQuery.osFamily()));
            }
            if (environmentQuery.systemMemoryReadable() != null) {
                predicates.add(criteriaBuilder.equal(root.get("systemMemoryReadable"), environmentQuery.systemMemoryReadable()));
            }
            if (environmentQuery.jmhVersion() != null) {
                predicates.add(criteriaBuilder.equal(root.get("jmhVersion"), environmentQuery.jmhVersion()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
