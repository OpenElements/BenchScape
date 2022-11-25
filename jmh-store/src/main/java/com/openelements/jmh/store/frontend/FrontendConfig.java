package com.openelements.jmh.store.frontend;

import com.openelements.jmh.store.db.DbConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import(DbConfiguration.class)
public class FrontendConfig {

}