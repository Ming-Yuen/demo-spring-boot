package com.demo.admin.service.impl;

import com.demo.admin.service.UserService;
import com.demo.security.dao.UserDao;
import com.demo.security.entity.User;
import com.demo.security.token.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private JwtManager jwt;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    @Value("${admin.adminId}")
    private String adminId;

    @Value("${admin.default.password}")
    private String adminDefaultPassword;

    @Value("${admin.default.emailAddress}")
    private String adminDefaultEmailAddress;

    @Override
    public String login(String username, String password) {
        User user = userDao.findByUserId(adminId);
        accountValid(password, user);

        return jwt.generateToken(user.getUserId(), user.getPassword());
    }

    public void accountValid(String password, User user){
        if(user == null){
            throw new IllegalArgumentException("User not found");
        }

        if(!passwordMatch(password, user)){
            throw new IllegalArgumentException("Password not correct");
        }
    }

    public boolean passwordMatch(String password, User admin){
        return passwordEncoder.matches(password, admin.getPassword());
    }
    @Override
    public User adminRegistration(){
        User admin = userDao.findByUserId(adminId);
        if(admin == null){
            admin = new User();
            admin.setEmail(adminDefaultEmailAddress);
            admin.setPassword(passwordEncoder.encode(adminDefaultPassword));
            admin.setGender("M");
            admin.setUserId(adminId);
            admin.setCreator(adminId);
            admin.setModifier(adminId);
            admin.setCreation_time(new Timestamp(System.currentTimeMillis()));
            admin.setModification_time(new Timestamp(System.currentTimeMillis()));
            userDao.save(admin);
        }
        return admin;
    }

    @Override
    public void update(User request) {
        User user = userDao.findByUserId(adminId);
        accountValid(request.getPassword(), user);
        userDao.save(user);
    }
}
