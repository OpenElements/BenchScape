package com.openelements.jmh.store.store.repositories;

import com.openelements.jmh.store.store.entities.MeasurementMetadataEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementMetadataRepository extends JpaRepository<MeasurementMetadataEntity, UUID> {

}
