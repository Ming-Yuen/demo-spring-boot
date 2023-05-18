package com.demo.common.listener;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Setter
@Getter
@ConfigurationProperties(prefix = "spring.datasource")
public class RefreshListener implements ApplicationListener<ContextRefreshedEvent> {

    private String url;

    private String username;

    private String password;

    private String driverClassName;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("catalina.base : {}", System.getProperty("catalina.base"));
        log.info("Database url : {}, username : {}", url, username);
    }
}