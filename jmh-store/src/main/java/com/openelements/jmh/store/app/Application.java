package com.openelements.jmh.store.app;

import com.openelements.jmh.store.security.SecurityPermitAllConfig;
import com.openelements.jmh.store.v2.ConfigV2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SecurityPermitAllConfig.class, ConfigV2.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
