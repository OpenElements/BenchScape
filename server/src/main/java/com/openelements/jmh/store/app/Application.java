package com.openelements.jmh.store.app;

import com.openelements.jmh.store.grafana.GrafanaConfiguration;
import com.openelements.jmh.store.security.SecurityPermitAllConfig;
import com.openelements.jmh.store.store.StoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SecurityPermitAllConfig.class, StoreConfig.class, GrafanaConfiguration.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
