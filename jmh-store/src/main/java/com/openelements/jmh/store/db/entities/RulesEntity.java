package com.openelements.jmh.store.db.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class RulesEntity {

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private Long benchmarkId;

  @Column
  private Double maxAllowedValue;

  @Column
  private Double minAllowedValue;

  @Column
  private Double maxAllowedError;

  @Column
  private Double maxAllowedErrorDeviation;

  @Column
  private Double maxAllowedValueDeviation;

  @Column(nullable = false)
  private boolean failOnDifferentProcessorCount;

  @Column(nullable = false)
  private boolean failOnDifferentMemorySize;

  @Column(nullable = false)
  private boolean failOnDifferentJvmVersion;

  public Long getId() {
    return id;
  }

  public void setId(final Long id) {
    this.id = id;
  }

  public Long getBenchmarkId() {
    return benchmarkId;
  }

  public void setBenchmarkId(final Long benchmarkId) {
    this.benchmarkId = benchmarkId;
  }

  public Double getMaxAllowedValue() {
    return maxAllowedValue;
  }

  public void setMaxAllowedValue(final Double maxAllowedValue) {
    this.maxAllowedValue = maxAllowedValue;
  }

  public Double getMinAllowedValue() {
    return minAllowedValue;
  }

  public void setMinAllowedValue(final Double minAllowedValue) {
    this.minAllowedValue = minAllowedValue;
  }

  public Double getMaxAllowedError() {
    return maxAllowedError;
  }

  public void setMaxAllowedError(final Double maxAllowedError) {
    this.maxAllowedError = maxAllowedError;
  }

  public Double getMaxAllowedErrorDeviation() {
    return maxAllowedErrorDeviation;
  }

  public void setMaxAllowedErrorDeviation(final Double maxAllowedErrorDeviation) {
    this.maxAllowedErrorDeviation = maxAllowedErrorDeviation;
  }

  public Double getMaxAllowedValueDeviation() {
    return maxAllowedValueDeviation;
  }

  public void setMaxAllowedValueDeviation(final Double maxAllowedValueDeviation) {
    this.maxAllowedValueDeviation = maxAllowedValueDeviation;
  }

  public boolean isFailOnDifferentProcessorCount() {
    return failOnDifferentProcessorCount;
  }

  public void setFailOnDifferentProcessorCount(final boolean failOnDifferentProcessorCount) {
    this.failOnDifferentProcessorCount = failOnDifferentProcessorCount;
  }

  public boolean isFailOnDifferentMemorySize() {
    return failOnDifferentMemorySize;
  }

  public void setFailOnDifferentMemorySize(final boolean failOnDifferentMemorySize) {
    this.failOnDifferentMemorySize = failOnDifferentMemorySize;
  }

  public boolean isFailOnDifferentJvmVersion() {
    return failOnDifferentJvmVersion;
  }

  public void setFailOnDifferentJvmVersion(final boolean failOnDifferentJvmVersion) {
    this.failOnDifferentJvmVersion = failOnDifferentJvmVersion;
  }
}
