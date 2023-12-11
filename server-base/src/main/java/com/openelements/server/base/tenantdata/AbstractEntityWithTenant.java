package com.openelements.server.base.tenantdata;

import com.openelements.server.base.data.AbstractEntity;
import com.openelements.server.base.tenant.WithTenant;
import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

@MappedSuperclass
public class AbstractEntityWithTenant extends AbstractEntity implements WithTenant {

    @Column(nullable = false)
    private String tenantId;

    @PrePersist
    @PreUpdate
    public void checkTenant() {
        if (tenantId == null) {
            throw new IllegalStateException("Tenant ID must be set before persisting");
        }
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }

    @Override
    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

}
