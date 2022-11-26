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

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getBenchmarkId() {
    return benchmarkId;
  }

  public void setBenchmarkId(Long benchmarkId) {
    this.benchmarkId = benchmarkId;
  }

  public Double getMaxAllowedValue() {
    return maxAllowedValue;
  }

  public void setMaxAllowedValue(Double maxAllowedValue) {
    this.maxAllowedValue = maxAllowedValue;
  }

  public Double getMinAllowedValue() {
    return minAllowedValue;
  }

  public void setMinAllowedValue(Double minAllowedValue) {
    this.minAllowedValue = minAllowedValue;
  }

  public Double getMaxAllowedError() {
    return maxAllowedError;
  }

  public void setMaxAllowedError(Double maxAllowedError) {
    this.maxAllowedError = maxAllowedError;
  }

  public Double getMaxAllowedErrorDeviation() {
    return maxAllowedErrorDeviation;
  }

  public void setMaxAllowedErrorDeviation(Double maxAllowedErrorDeviation) {
    this.maxAllowedErrorDeviation = maxAllowedErrorDeviation;
  }

  public Double getMaxAllowedValueDeviation() {
    return maxAllowedValueDeviation;
  }

  public void setMaxAllowedValueDeviation(Double maxAllowedValueDeviation) {
    this.maxAllowedValueDeviation = maxAllowedValueDeviation;
  }
}
