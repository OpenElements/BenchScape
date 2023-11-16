package com.openelements.benchscape.server.store.repositories;

import com.openelements.benchscape.server.store.entities.MeasurementMetadataEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementMetadataRepository extends JpaRepository<MeasurementMetadataEntity, UUID> {

}
