package com.demo.admin.service.impl;

import com.demo.admin.dao.UserDao;
import com.demo.admin.eneity.UserEntity;
import com.demo.admin.service.AdminService;
import com.demo.token.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ldap.embedded.EmbeddedLdapProperties;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private JwtManager jwt;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;
    @Override
    public String login(String username, String password) {
        UserEntity user = getUser(username, password);
        if(user == null){
            throw new IllegalArgumentException("User not found");
        }

        return jwt.generateToken(user.getUserName(), user.getPassword());
    }

    @Override
    public UserEntity getUser(String username, String password) {
        return userDao.findByUserNameAndPassword(username, passwordEncoder.encode(password));
    }

}
