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

  public Long getBenchmarkId() {
    return benchmarkId;
  }

  public void setBenchmarkId(Long benchmarkId) {
    this.benchmarkId = benchmarkId;
  }

  public Instant getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Instant timestamp) {
    this.timestamp = timestamp;
  }

  public double getMeasurement() {
    return measurement;
  }

  public void setMeasurement(double value) {
    this.measurement = value;
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
