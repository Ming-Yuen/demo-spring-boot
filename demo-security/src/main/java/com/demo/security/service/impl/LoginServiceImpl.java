package com.demo.security.service.impl;

import com.demo.common.dao.UserDao;
import com.demo.common.dao.UserRoleDao;
import com.demo.common.entity.User;
import com.demo.common.entity.UserRole;
import com.demo.common.util.UserContextHolder;
import com.demo.security.service.LoginService;
import com.demo.security.token.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;
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
        User admin = userDao.findByUsername("SuperAdmin");
        if(admin == null){
            admin = new User();
            admin.setEmail(adminDefaultEmailAddress);
            admin.setPassword(passwordEncoder.encode(adminDefaultPassword));
            admin.setGender("M");
            admin.setUsername("SuperAdmin");
            admin.setRoleId(1L);
            UserContextHolder.setUser(admin);
            userDao.save(admin);
        }
        admin = userDao.findByUsername(adminId);
        if(admin == null){
            admin = new User();
            admin.setEmail(adminDefaultEmailAddress);
            admin.setPassword(passwordEncoder.encode(adminDefaultPassword));
            admin.setGender("M");
            admin.setUsername(adminId);
            admin.setRoleId(2L);
            UserContextHolder.setUser(admin);
            userDao.save(admin);
        }
        admin = userDao.findByUsername("user");
        if(admin == null){
            admin = new User();
            admin.setEmail(adminDefaultEmailAddress);
            admin.setPassword(passwordEncoder.encode(adminDefaultPassword));
            admin.setGender("M");
            admin.setUsername("user");
            admin.setRoleId(3L);
            UserContextHolder.setUser(admin);
            userDao.save(admin);
        }
        UserRole userRole = userRoleDao.findByUid(1L);
        if(userRole == null){
            userRole = new UserRole();
            userRole.setName("Super admin");
            userRole.setRoleLevel(1);
            userRoleDao.save(userRole);
        }
        userRole = userRoleDao.findByUid(2L);
        if(userRole == null){
            userRole = new UserRole();
            userRole.setName("admin");
            userRole.setRoleLevel(2);
            userRoleDao.save(userRole);
        }
        userRole = userRoleDao.findByUid(3L);
        if(userRole == null){
            userRole = new UserRole();
            userRole.setName("user");
            userRole.setRoleLevel(3);
            userRoleDao.save(userRole);
        }
        return admin;
    }

    @Override
    public String login(String username, String password) {
        User user = userDao.findByUsername(username);
        if(!passwordMatch(password, user)){
            throw new IllegalArgumentException("Incorrect password");
        }

        return jwt.generateToken(user.getUsername(), user.getPassword());
    }

    public boolean passwordMatch(String password, User admin){
        return passwordEncoder.matches(password, admin.getPassword());
    }
}
