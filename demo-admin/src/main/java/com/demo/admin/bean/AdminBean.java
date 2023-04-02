package com.demo.admin.bean;

import com.demo.admin.service.AdminService;
import com.demo.security.dao.AdminDao;
import com.demo.security.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class AdminBean {

    @Autowired
    private AdminService adminService;

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public Admin getAdmin(){
        return adminService.adminRegistration();
    }
}
