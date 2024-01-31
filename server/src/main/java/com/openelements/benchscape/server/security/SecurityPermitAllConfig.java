package com.openelements.benchscape.server.security;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * This class is used to configure Spring Security to allow all requests. This will change in future versions of the
 * application.
 */
@Configuration
@EnableAutoConfiguration
@ComponentScan
public class SecurityPermitAllConfig {

    /**
     * This method configures Spring Security to allow all requests.
     *
     * @param http the {@link HttpSecurity} object to configure
     * @return the {@link SecurityFilterChain} object
     * @throws Exception if an error occurs while configuring Spring Security
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests((authorizeRequests) -> authorizeRequests.anyRequest().permitAll())
                .csrf().disable().build();
    }
}
