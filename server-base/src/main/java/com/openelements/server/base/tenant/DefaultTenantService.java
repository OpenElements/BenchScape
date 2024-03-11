package com.openelements.server.base.tenant;

import com.openelements.server.base.auth.UserService;
import edu.umd.cs.findbugs.annotations.NonNull;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class DefaultTenantService implements TenantService {

    private final UserService userService;

    public DefaultTenantService(@NonNull final UserService userService) {
        this.userService = Objects.requireNonNull(userService, "userService must not be null");
    }

    @Override
    public String getCurrentTenant() {
        return userService.getCurrentUser().mailAddress();
    }
}
