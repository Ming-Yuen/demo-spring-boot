package com.demo.security.service.impl;

import com.demo.common.dao.UserDao;
import com.demo.common.entity.User;
import com.demo.security.service.LoginService;
import com.demo.security.token.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtManager jwt;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${admin.adminId}")
    private String adminId;

    @Value("${admin.default.password}")
    private String adminDefaultPassword;

    @Value("${admin.default.emailAddress}")
    private String adminDefaultEmailAddress;

    @Override
    public User adminRegistration(){
        User admin = userDao.findByUsername(adminId);
        if(admin == null){
            admin = new User();
            admin.setEmail(adminDefaultEmailAddress);
            admin.setPassword(passwordEncoder.encode(adminDefaultPassword));
            admin.setGender("M");
            admin.setUsername(adminId);
            admin.setUserRole("admin");
            admin.setCreator(adminId);
            admin.setModifier(adminId);
            admin.setCreation_time(new Timestamp(System.currentTimeMillis()));
            admin.setModification_time(new Timestamp(System.currentTimeMillis()));
            admin.setTx_creation_time(new Timestamp(System.currentTimeMillis()));
            admin.setTx_modification_time(new Timestamp(System.currentTimeMillis()));
            userDao.save(admin);
        }
        return admin;
    }

    @Override
    public String login(String username, String password) {
        User user = userDao.findByUsername(username);
        if(!passwordMatch(password, user)){
            throw new IllegalArgumentException("Password not correct");
        }

        return jwt.generateToken(user.getUsername(), user.getPassword());
    }

    public boolean passwordMatch(String password, User admin){
        return passwordEncoder.matches(password, admin.getPassword());
    }
}
