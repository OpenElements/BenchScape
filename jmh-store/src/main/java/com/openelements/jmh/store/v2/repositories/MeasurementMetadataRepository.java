package com.openelements.jmh.store.v2.repositories;

import com.openelements.jmh.store.v2.entities.MeasurementMetadataEntity;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementMetadataRepository extends JpaRepository<MeasurementMetadataEntity, UUID> {

    @NonNull
    Optional<MeasurementMetadataEntity> findByMeasurementId(@NonNull final UUID measurementId);

}
