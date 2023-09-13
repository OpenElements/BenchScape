package com.openelements.jmh.store.app;

import com.openelements.jmh.store.db.DbConfiguration;
import com.openelements.jmh.store.endpoint.EndpointConfiguration;
import com.openelements.jmh.store.frontend.FrontendConfig;
import com.openelements.jmh.store.grafana.GrafanaConfiguration;
import com.openelements.jmh.store.security.SecurityPermitAllConfig;
import com.openelements.jmh.store.swagger.SwaggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({DbConfiguration.class, EndpointConfiguration.class, FrontendConfig.class,
        GrafanaConfiguration.class, SwaggerConfig.class, SecurityPermitAllConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
