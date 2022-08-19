package com.example.demo.data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(indexes = @Index(columnList = "benchmarkId"))
public class TimeseriesEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String benchmarkId;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private double value;

    @Column(nullable = false)
    private double error;

    @Column(nullable = false)
    private double min;

    @Column(nullable = false)
    private double max;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getBenchmarkId() {
        return benchmarkId;
    }

    public void setBenchmarkId(String benchmarkId) {
        this.benchmarkId = benchmarkId;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getError() {
        return error;
    }

    public void setError(double error) {
        this.error = error;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}
