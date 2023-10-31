package org.demo.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@ComponentScan(basePackages = "com.demo")
@SpringBootApplication
@EnableWebMvc
@EnableCaching
@EnableScheduling
public class DemoOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoOrderApplication.class);
    }
}