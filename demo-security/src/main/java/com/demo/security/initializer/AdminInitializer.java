package com.demo.security.initializer;

import com.demo.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {
    @Autowired
    private LoginService adminService;
    @Override
    public void run(String... args) throws Exception {
        adminService.adminRegistration();
    }
}
