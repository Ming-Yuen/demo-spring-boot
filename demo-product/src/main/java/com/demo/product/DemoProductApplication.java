package com.demo.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo.product")
@EnableWebMvc
@EnableCaching
@EnableScheduling
public class DemoProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoProductApplication.class);
    }
}
