package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(basePackages = {"com.demo","com.demo.common", "com.demo.product"})
@SpringBootApplication
@EnableWebMvc
@EnableCaching
@EnableScheduling
//@EnableJpaRepositories("com.demo.common")
public class DemoProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoProductApplication.class);
    }
}
