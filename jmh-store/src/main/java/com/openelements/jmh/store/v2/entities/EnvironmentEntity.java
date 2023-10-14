package com.openelements.jmh.store.v2.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class EnvironmentEntity {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private String gitOriginUrl;

    @Column
    private String gitBranch;

    @Column
    private String systemArch;

    @Column
    private Integer systemProcessors;

    @Column
    private Integer systemProcessorsMin;

    @Column
    private Integer systemProcessorsMax;

    @Column
    private Long systemMemory;

    @Column
    private Long systemMemoryMin;

    @Column
    private Long systemMemoryMax;

    @Column
    private String osName;

    @Column
    private String osVersion;

    @Column
    private String jvmVersion;

    @Column
    private String jvmName;

    @Column
    private String jmhVersion;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getGitOriginUrl() {
        return gitOriginUrl;
    }

    public void setGitOriginUrl(String gitOriginUrl) {
        this.gitOriginUrl = gitOriginUrl;
    }

    public String getGitBranch() {
        return gitBranch;
    }

    public void setGitBranch(String gitBranch) {
        this.gitBranch = gitBranch;
    }

    public String getSystemArch() {
        return systemArch;
    }

    public void setSystemArch(String systemArch) {
        this.systemArch = systemArch;
    }

    public Integer getSystemProcessors() {
        return systemProcessors;
    }

    public void setSystemProcessors(Integer systemProcessors) {
        this.systemProcessors = systemProcessors;
    }

    public Integer getSystemProcessorsMin() {
        return systemProcessorsMin;
    }

    public void setSystemProcessorsMin(Integer systemProcessorsMin) {
        this.systemProcessorsMin = systemProcessorsMin;
    }

    public Integer getSystemProcessorsMax() {
        return systemProcessorsMax;
    }

    public void setSystemProcessorsMax(Integer systemProcessorsMax) {
        this.systemProcessorsMax = systemProcessorsMax;
    }

    public Long getSystemMemory() {
        return systemMemory;
    }

    public void setSystemMemory(Long systemMemory) {
        this.systemMemory = systemMemory;
    }

    public Long getSystemMemoryMin() {
        return systemMemoryMin;
    }

    public void setSystemMemoryMin(Long systemMemoryMin) {
        this.systemMemoryMin = systemMemoryMin;
    }

    public Long getSystemMemoryMax() {
        return systemMemoryMax;
    }

    public void setSystemMemoryMax(Long systemMemoryMax) {
        this.systemMemoryMax = systemMemoryMax;
    }

    public String getOsName() {
        return osName;
    }

    public void setOsName(String osName) {
        this.osName = osName;
    }

    public String getOsVersion() {
        return osVersion;
    }

    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }

    public String getJvmVersion() {
        return jvmVersion;
    }

    public void setJvmVersion(String jvmVersion) {
        this.jvmVersion = jvmVersion;
    }

    public String getJvmName() {
        return jvmName;
    }

    public void setJvmName(String jvmName) {
        this.jvmName = jvmName;
    }

    public String getJmhVersion() {
        return jmhVersion;
    }

    public void setJmhVersion(String jmhVersion) {
        this.jmhVersion = jmhVersion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EnvironmentEntity that = (EnvironmentEntity) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name)
                && Objects.equals(description, that.description) && Objects.equals(gitOriginUrl,
                that.gitOriginUrl) && Objects.equals(gitBranch, that.gitBranch) && Objects.equals(
                systemArch, that.systemArch) && Objects.equals(systemProcessors, that.systemProcessors)
                && Objects.equals(systemProcessorsMin, that.systemProcessorsMin) && Objects.equals(
                systemProcessorsMax, that.systemProcessorsMax) && Objects.equals(systemMemory, that.systemMemory)
                && Objects.equals(systemMemoryMin, that.systemMemoryMin) && Objects.equals(
                systemMemoryMax, that.systemMemoryMax) && Objects.equals(osName, that.osName)
                && Objects.equals(osVersion, that.osVersion) && Objects.equals(jvmVersion,
                that.jvmVersion) && Objects.equals(jvmName, that.jvmName) && Objects.equals(jmhVersion,
                that.jmhVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, gitOriginUrl, gitBranch, systemArch, systemProcessors,
                systemProcessorsMin, systemProcessorsMax, systemMemory, systemMemoryMin, systemMemoryMax, osName,
                osVersion,
                jvmVersion, jvmName, jmhVersion);
    }
}
