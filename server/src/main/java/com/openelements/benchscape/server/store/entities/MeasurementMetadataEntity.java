package com.openelements.benchscape.server.store.entities;

import com.openelements.server.base.tenantdata.AbstractEntityWithTenant;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import java.time.Duration;
import java.util.Objects;
import java.util.Set;

@Entity(name = "MeasurementMetadata")
public class MeasurementMetadataEntity extends AbstractEntityWithTenant {

    @Column
    private String gitOriginUrl;

    @Column
    private String gitBranch;

    @Column
    private String gitCommitId;

    @ElementCollection(fetch = FetchType.EAGER)
    private Set<String> gitTags;

    @Column
    private Boolean gitDirty;

    @Column
    private Integer jmhThreadCount;

    @Column
    private Integer jmhForks;

    @Column
    private Duration jmhTimeout;

    @Column
    private Integer jmhWarmupIterations;

    @Column
    private Duration jmhWarmupTime;

    @Column
    private Integer jmhWarmupBatchSize;

    @Column
    private Integer jmhMeasurementIterations;

    @Column
    private Duration jmhMeasurementTime;

    @Column
    private Integer jmhMeasurementBatchSize;

    @Column
    private String systemArch;

    @Column
    private Integer systemProcessors;

    @Column
    private Long systemMemory;

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

    public String getGitCommitId() {
        return gitCommitId;
    }

    public void setGitCommitId(String gitCommitId) {
        this.gitCommitId = gitCommitId;
    }

    public Set<String> getGitTags() {
        return gitTags;
    }

    public void setGitTags(Set<String> gitTags) {
        this.gitTags = gitTags;
    }

    public Boolean getGitDirty() {
        return gitDirty;
    }

    public void setGitDirty(Boolean gitDirty) {
        this.gitDirty = gitDirty;
    }

    public Integer getJmhThreadCount() {
        return jmhThreadCount;
    }

    public void setJmhThreadCount(Integer jmhThreadCount) {
        this.jmhThreadCount = jmhThreadCount;
    }

    public Integer getJmhForks() {
        return jmhForks;
    }

    public void setJmhForks(Integer jmhForks) {
        this.jmhForks = jmhForks;
    }

    public Duration getJmhTimeout() {
        return jmhTimeout;
    }

    public void setJmhTimeout(Duration jmhTimeout) {
        this.jmhTimeout = jmhTimeout;
    }

    public Integer getJmhWarmupIterations() {
        return jmhWarmupIterations;
    }

    public void setJmhWarmupIterations(Integer jmhWarmupIterations) {
        this.jmhWarmupIterations = jmhWarmupIterations;
    }

    public Duration getJmhWarmupTime() {
        return jmhWarmupTime;
    }

    public void setJmhWarmupTime(Duration jmhWarmupTime) {
        this.jmhWarmupTime = jmhWarmupTime;
    }

    public Integer getJmhWarmupBatchSize() {
        return jmhWarmupBatchSize;
    }

    public void setJmhWarmupBatchSize(Integer jmhWarmupBatchSize) {
        this.jmhWarmupBatchSize = jmhWarmupBatchSize;
    }

    public Integer getJmhMeasurementIterations() {
        return jmhMeasurementIterations;
    }

    public void setJmhMeasurementIterations(Integer jmhMeasurementIterations) {
        this.jmhMeasurementIterations = jmhMeasurementIterations;
    }

    public Duration getJmhMeasurementTime() {
        return jmhMeasurementTime;
    }

    public void setJmhMeasurementTime(Duration jmhMeasurementTime) {
        this.jmhMeasurementTime = jmhMeasurementTime;
    }

    public Integer getJmhMeasurementBatchSize() {
        return jmhMeasurementBatchSize;
    }

    public void setJmhMeasurementBatchSize(Integer jmhMeasurementBatchSize) {
        this.jmhMeasurementBatchSize = jmhMeasurementBatchSize;
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

    public Long getSystemMemory() {
        return systemMemory;
    }

    public void setSystemMemory(Long systemMemory) {
        this.systemMemory = systemMemory;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MeasurementMetadataEntity that = (MeasurementMetadataEntity) o;
        return Objects.equals(getId(), that.getId())
                && Objects.equals(gitOriginUrl, that.gitOriginUrl) && Objects.equals(gitBranch,
                that.gitBranch) && Objects.equals(gitCommitId, that.gitCommitId) && Objects.equals(
                gitTags, that.gitTags) && Objects.equals(gitDirty, that.gitDirty) && Objects.equals(
                jmhThreadCount, that.jmhThreadCount) && Objects.equals(jmhForks, that.jmhForks)
                && Objects.equals(jmhTimeout, that.jmhTimeout) && Objects.equals(jmhWarmupIterations,
                that.jmhWarmupIterations) && Objects.equals(jmhWarmupTime, that.jmhWarmupTime)
                && Objects.equals(jmhWarmupBatchSize, that.jmhWarmupBatchSize) && Objects.equals(
                jmhMeasurementIterations, that.jmhMeasurementIterations) && Objects.equals(jmhMeasurementTime,
                that.jmhMeasurementTime) && Objects.equals(jmhMeasurementBatchSize, that.jmhMeasurementBatchSize)
                && Objects.equals(systemArch, that.systemArch) && Objects.equals(systemProcessors,
                that.systemProcessors) && Objects.equals(systemMemory, that.systemMemory)
                && Objects.equals(osName, that.osName) && Objects.equals(osVersion, that.osVersion)
                && Objects.equals(jvmVersion, that.jvmVersion) && Objects.equals(jvmName, that.jvmName)
                && Objects.equals(jmhVersion, that.jmhVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), gitOriginUrl, gitBranch, gitCommitId, gitTags, gitDirty, jmhThreadCount,
                jmhForks, jmhTimeout, jmhWarmupIterations, jmhWarmupTime, jmhWarmupBatchSize, jmhMeasurementIterations,
                jmhMeasurementTime, jmhMeasurementBatchSize, systemArch, systemProcessors, systemMemory, osName,
                osVersion,
                jvmVersion, jvmName, jmhVersion);
    }
}
