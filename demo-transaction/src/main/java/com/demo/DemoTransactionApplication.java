package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableCaching
@EnableScheduling
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class DemoTransactionApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoTransactionApplication.class);
    }
}
