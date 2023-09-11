package com.openelements.jmh.store.db.entities;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Table(indexes = @Index(columnList = "benchmarkId"))
public class TimeseriesEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private Long benchmarkId;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private double measurement;

    @Column(nullable = true)
    private Double error;

    @Column(nullable = true)
    private Double min;

    @Column(nullable = true)
    private Double max;

    @Column(nullable = true)
    private Integer availableProcessors;

    @Column(nullable = true)
    private Long memory;

    @Column(nullable = true)
    private String jvmVersion;

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getBenchmarkId() {
        return benchmarkId;
    }

    public void setBenchmarkId(final Long benchmarkId) {
        this.benchmarkId = benchmarkId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final Instant timestamp) {
        this.timestamp = timestamp;
    }

    public double getMeasurement() {
        return measurement;
    }

    public void setMeasurement(final double value) {
        this.measurement = value;
    }

    public Double getError() {
        return error;
    }

    public void setError(final Double error) {
        this.error = error;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(final Double min) {
        this.min = min;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(final Double max) {
        this.max = max;
    }

    public Integer getAvailableProcessors() {
        return availableProcessors;
    }

    public void setAvailableProcessors(final Integer availableProcessors) {
        this.availableProcessors = availableProcessors;
    }

    public Long getMemory() {
        return memory;
    }

    public void setMemory(final Long memory) {
        this.memory = memory;
    }

    public String getJvmVersion() {
        return jvmVersion;
    }

    public void setJvmVersion(final String jvmVersion) {
        this.jvmVersion = jvmVersion;
    }
}
