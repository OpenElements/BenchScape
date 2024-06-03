package com.openelements.benchscape.server.store.repositories;

import com.openelements.benchscape.server.store.data.MeasurementQuery;
import com.openelements.benchscape.server.store.entities.MeasurementEntity;
import com.openelements.benchscape.server.store.entities.MeasurementEntity_;
import org.springframework.data.jpa.domain.Specification;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MeasurementSpecification {

    public static Specification<MeasurementEntity> createSpecification(MeasurementQuery query) {
        return (root, query1, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(cb.equal(root.get(MeasurementEntity_.benchmarkId), UUID.fromString(query.benchmarkId())));

            predicates.add(cb.between(root.get(MeasurementEntity_.timestamp), query.start(), query.end()));

            if (query.gitOriginUrl() != null) {
                predicates.add(cb.equal(root.get(MeasurementEntity_.metadata).get("gitOriginUrl"), query.gitOriginUrl()));
            }

            if (query.gitBranch() != null) {
                predicates.add(cb.equal(root.get(MeasurementEntity_.metadata).get("gitBranch"), query.gitBranch()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
