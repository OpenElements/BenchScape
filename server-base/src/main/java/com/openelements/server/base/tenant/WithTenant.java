package com.openelements.server.base.tenant;

public interface WithTenant {

    String getTenantId();

    void setTenantId(String tenantId);
}
