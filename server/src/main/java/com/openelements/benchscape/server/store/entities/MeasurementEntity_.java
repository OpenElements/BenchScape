package com.openelements.benchscape.server.store.entities;

import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.time.Instant;
import java.util.UUID;

@StaticMetamodel(MeasurementEntity.class)
public class MeasurementEntity_ {
    public static volatile SingularAttribute<MeasurementEntity, UUID> benchmarkId;
    public static volatile SingularAttribute<MeasurementEntity, Instant> timestamp;
    public static volatile SingularAttribute<MeasurementEntity, Double> value;
    public static volatile SingularAttribute<MeasurementEntity, Double> error;
    public static volatile SingularAttribute<MeasurementEntity, Double> min;
    public static volatile SingularAttribute<MeasurementEntity, Double> max;
    public static volatile SingularAttribute<MeasurementEntity, String> comment;
    public static volatile SingularAttribute<MeasurementEntity, MeasurementMetadataEntity> metadata;
    public static volatile SingularAttribute<MeasurementEntity, String> environmentId;
}
