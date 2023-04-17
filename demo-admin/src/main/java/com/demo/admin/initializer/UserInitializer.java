package com.demo.admin.initializer;

import com.demo.admin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

public class UserInitializer implements CommandLineRunner {

    @Autowired
    private UserService adminService;
    @Override
    public void run(String... args) throws Exception {
        adminService.adminRegistration();
    }
}
