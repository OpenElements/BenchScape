package com.openelements.jmh.store.db.entities;

import com.openelements.benchscape.common.BenchmarkUnit;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BenchmarkEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private BenchmarkUnit unit;

    @Column(nullable = true)
    private Integer defaultAvailableProcessors;

    @Column(nullable = true)
    private Long defaultMemory;

    @Column(nullable = true)
    private String defaultJvmVersion;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public BenchmarkUnit getUnit() {
        return unit;
    }

    public void setUnit(BenchmarkUnit unit) {
        this.unit = unit;
    }

    public Integer getDefaultAvailableProcessors() {
        return defaultAvailableProcessors;
    }

    public void setDefaultAvailableProcessors(final Integer defaultAvailableProcessors) {
        this.defaultAvailableProcessors = defaultAvailableProcessors;
    }

    public Long getDefaultMemory() {
        return defaultMemory;
    }

    public void setDefaultMemory(final Long defaultMemory) {
        this.defaultMemory = defaultMemory;
    }

    public String getDefaultJvmVersion() {
        return defaultJvmVersion;
    }

    public void setDefaultJvmVersion(final String defaultJvmVersion) {
        this.defaultJvmVersion = defaultJvmVersion;
    }
}
