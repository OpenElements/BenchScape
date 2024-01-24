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

    @NonNull
    private static Specification<EnvironmentEntity> createSpecificationForQuery(
            @NonNull final EnvironmentQuery environmentQuery) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (environmentQuery.systemArch() != null) {
                predicates.add(criteriaBuilder.equal(root.get("systemArch"), environmentQuery.systemArch()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
