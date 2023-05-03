package com.demo.admin.service.impl;

import com.demo.admin.dao.UserDao;
import com.demo.admin.entity.User;
import com.demo.admin.service.UserService;
import com.demo.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisService redis;

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
            admin.setPassword(adminDefaultPassword);
            admin.setGender("M");
            admin.setUsername(adminId);
            admin.setRole("admin");
            admin.setCreator(adminId);
            admin.setModifier(adminId);
            admin.setCreation_time(new Timestamp(System.currentTimeMillis()));
            admin.setModification_time(new Timestamp(System.currentTimeMillis()));
            userDao.save(admin);
        }
        return admin;
    }

    @Override
    public void register(User request){
        User user = findByUsername(request.getUsername());
        if(user != null){
            throw new IllegalArgumentException("User already registered");
        }
        userDao.save(request);
    }

    @Override
    public void update(User request) {
        User user = findByUsername(request.getUsername());
        if(user == null){
            throw new IllegalArgumentException("User not found");
        }
        userDao.save(user);
    }
    @Override
    public User findByUsername(String userId){
        User user = (User) redis.get(userId);
        if(user == null){
            user = userDao.findByUsername(userId);
            redis.set(userId, user);
        }
        return user;
    }
}
