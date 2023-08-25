package com.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.net.MalformedURLException;

@ComponentScan(basePackages = "com.demo")
@SpringBootApplication
public class DemoAdminApplication {

    public static void main(String[] args) throws MalformedURLException {
        SpringApplication.run(DemoAdminApplication.class, args);
    }
}