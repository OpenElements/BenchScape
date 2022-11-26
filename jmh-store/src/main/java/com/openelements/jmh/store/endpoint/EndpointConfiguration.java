package com.openelements.jmh.store.endpoint;

import com.openelements.jmh.store.db.DbConfiguration;
import com.openelements.jmh.store.gate.QualityGateConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import({DbConfiguration.class, QualityGateConfiguration.class})
public class EndpointConfiguration {

}
