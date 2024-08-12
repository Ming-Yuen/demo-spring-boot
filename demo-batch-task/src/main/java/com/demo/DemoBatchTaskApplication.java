package com.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableCaching
@EnableScheduling
@MapperScan(basePackages = {"com.demo.admin.dao", "com.demo.product.dao", "com.demo.transaction.dao", "com.demo.batckTask.dao"})
public class DemoBatchTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoBatchTaskApplication.class, args);
    }
}