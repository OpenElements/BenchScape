package com.openelements.benchscape.server.app;

import com.openelements.benchscape.server.security.SecurityPermitAllConfig;
import com.openelements.benchscape.server.store.StoreConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({SecurityPermitAllConfig.class, StoreConfig.class})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
