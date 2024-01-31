package com.openelements.benchscape.server.app;

import com.openelements.benchscape.server.security.SecurityPermitAllConfig;
import com.openelements.benchscape.server.store.StoreConfig;
import com.openelements.benchscape.server.tenant.SimpleTenantConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * The main application class. This class starts the Spring Boot application.
 */
@SpringBootApplication
@Import({SecurityPermitAllConfig.class, StoreConfig.class, SimpleTenantConfig.class})
public class Application {

    /**
     * The main method. This method starts the Spring Boot application.
     *
     * @param args the command line arguments (not custom args for this app)
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
