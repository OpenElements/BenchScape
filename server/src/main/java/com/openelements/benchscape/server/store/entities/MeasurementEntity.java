package com.openelements.benchscape.server.store.entities;

import com.openelements.benchscape.jmh.model.BenchmarkUnit;
import com.openelements.server.base.tenantdata.AbstractEntityWithTenant;
import jakarta.persistence.*;
import jakarta.persistence.metamodel.StaticMetamodel;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

@StaticMetamodel(MeasurementEntity.class)
@Entity(name = "Measurement")
public class MeasurementEntity extends AbstractEntityWithTenant {

    @Column(nullable = false)
    private UUID benchmarkId;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false, name = "measurement")
    private double value;

    @Column
    private Double error;

    @Column
    private Double min;

    @Column
    private Double max;

    @Column(nullable = false)
    private BenchmarkUnit unit;

    @Column
    private String comment;

    @OneToOne(fetch = FetchType.LAZY, orphanRemoval = true, optional = false, cascade = CascadeType.ALL)
    private MeasurementMetadataEntity metadata;

    public UUID getBenchmarkId() {
        return benchmarkId;
    }

    public void setBenchmarkId(UUID benchmarkId) {
        this.benchmarkId = benchmarkId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public Double getError() {
        return error;
    }

    public void setError(Double error) {
        this.error = error;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public BenchmarkUnit getUnit() {
        return unit;
    }

    public void setUnit(BenchmarkUnit unit) {
        this.unit = unit;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public MeasurementMetadataEntity getMetadata() {
        return metadata;
    }

    public void setMetadata(MeasurementMetadataEntity metadata) {
        this.metadata = metadata;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MeasurementEntity that = (MeasurementEntity) o;
        return Double.compare(value, that.value) == 0 && Objects.equals(benchmarkId, that.benchmarkId)
                && Objects.equals(timestamp, that.timestamp) && Objects.equals(error, that.error)
                && Objects.equals(min, that.min) && Objects.equals(max, that.max) && unit == that.unit
                && Objects.equals(comment, that.comment) && Objects.equals(metadata, that.metadata);
    }

    @Override
    public int hashCode() {
        return Objects.hash(benchmarkId, timestamp, value, error, min, max, unit, comment, metadata);
    }
}
