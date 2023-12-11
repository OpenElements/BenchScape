package com.openelements.benchscape.server.tenant;

import com.openelements.server.base.tenant.TenantService;
import org.springframework.stereotype.Service;

@Service
public class SimpleTenantService implements TenantService {

    private final static String DEFAULT_TENANT = "defaultTenant";

    @Override
    public String getCurrentTenant() {
        return DEFAULT_TENANT;
    }
}
