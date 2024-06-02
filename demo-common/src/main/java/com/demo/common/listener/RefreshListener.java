package com.demo.common.listener;

import lombok.Data;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Data
@ConfigurationProperties(prefix = "spring.datasource")
public class RefreshListener implements CommandLineRunner {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    @Override
    public void run(String... args) throws Exception {
        log.info("catalina.base : {}", System.getProperty("catalina.base"));
        log.info("Database url : {}, username : {}", url, username);
    }
}