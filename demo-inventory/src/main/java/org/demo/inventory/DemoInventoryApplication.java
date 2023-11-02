package org.demo.inventory;

import com.demo.common.dto.DeltaResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "org.demo.inventory")
@SpringBootApplication
public class DemoInventoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoInventoryApplication.class);
    }
}