package com.demo.admin.service.impl;

import com.demo.admin.service.AdminService;
import com.demo.security.dao.AdminDao;
import com.demo.security.entity.Admin;
import com.demo.security.token.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private JwtManager jwt;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminDao adminDao;

    @Value("${admin.adminId}")
    private String adminId;

    @Value("${admin.default.password}")
    private String adminDefaultPassword;

    @Value("${admin.default.emailAddress}")
    private String adminDefaultEmailAddress;

    @Override
    public String login(String username, String password) {
        Admin user = getUser(username, password);
        if(user == null){
            throw new IllegalArgumentException("User not found");
        }

        return jwt.generateToken(user.getAdminId(), user.getPassword());
    }

    @Override
    public Admin getUser(String username, String password) {
        Admin admin = adminDao.findByAdminId(adminId);
        if(admin != null && passwordEncoder.matches(password, admin.getPassword())){
            return admin;
        }
        return null;
    }
    @Override
    public Admin adminRegistration(){
        Admin admin = adminDao.findByAdminId(adminId);
        if(admin == null){
            admin = new Admin();
            admin.setEmail(adminDefaultEmailAddress);
            admin.setPassword(passwordEncoder.encode(adminDefaultPassword));
            admin.setGender("M");
            admin.setAdminId(adminId);
            admin.setCreator(adminId);
            admin.setModifier(adminId);
            admin.setCreation_time(new Timestamp(System.currentTimeMillis()));
            admin.setModification_time(new Timestamp(System.currentTimeMillis()));
            adminDao.save(admin);
        }
        return admin;
    }
}
