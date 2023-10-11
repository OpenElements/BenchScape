package com.openelements.jmh.store.v2.repositories;

import com.openelements.jmh.store.v2.entities.MeasurementMetadataEntity;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MeasurementMetadataRepository extends JpaRepository<MeasurementMetadataEntity, UUID> {

    @NonNull
    Optional<MeasurementMetadataEntity> findByMeasurementId(@NonNull final String measurementId);

}
