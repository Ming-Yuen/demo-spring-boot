package com.demo.security.service.impl;

import com.demo.security.dao.UserDao;
import com.demo.security.model.User;
import com.demo.security.service.LoginService;
import com.demo.security.token.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private JwtManager jwt;

    @Autowired
    private PasswordEncoder passwordEncoder;


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
