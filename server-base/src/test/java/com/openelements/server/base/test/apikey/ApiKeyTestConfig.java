package com.openelements.server.base.test.apikey;

import com.openelements.server.base.apikey.UserService;
import com.openelements.server.base.tenant.TenantService;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfiguration
@EnableAutoConfiguration
@ComponentScan
public class ApiKeyTestConfig {

    public static final String TEST_TENANT = "test-tenant";
    public static final String TEST_USER = "test-user";

    @Bean
    public TenantService tenantService() {
        return new TenantService() {
            @Override
            public String getCurrentTenant() {
                return TEST_TENANT;
            }
        };
    }

    @Bean
    public UserService userService() {
        return new UserService() {
            @Override
            public String getCurrentUser() {
                return TEST_USER;
            }
        };
    }
}
