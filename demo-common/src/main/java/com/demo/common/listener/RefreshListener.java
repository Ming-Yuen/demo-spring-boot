package com.demo.common.listener;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "spring.datasource")
@Order(-1)
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