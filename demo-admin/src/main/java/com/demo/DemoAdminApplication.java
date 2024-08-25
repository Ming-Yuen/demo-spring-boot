package com.demo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
@SpringBootApplication
@EnableWebMvc
@EnableCaching
@EnableDiscoveryClient
public class DemoAdminApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoAdminApplication.class, args);
    }
}