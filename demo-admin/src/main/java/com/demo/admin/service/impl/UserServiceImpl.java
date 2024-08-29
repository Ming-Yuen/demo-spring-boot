package com.demo.admin.service.impl;

import com.demo.admin.dao.PrivilegeMapper;
import com.demo.admin.dao.UserMapper;
import com.demo.common.dto.UserRegisterRequest;
import com.demo.admin.entity.Users;
import com.demo.admin.mapping.UserMapping;
import com.demo.admin.security.JwtUtil;
import com.demo.admin.service.UserService;
import com.github.pagehelper.PageHelper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserMapper userMapper;
    private UserMapping userMapping;
    private PrivilegeMapper privilegeMapper;
    private JwtUtil jwt;
    private Long expiration;

    public UserServiceImpl(UserMapper userMapper, UserMapping userMapping, PrivilegeMapper privilegeMapper, JwtUtil jwt, @Value("${jwt.expiration}") Long expiration) {
        this.userMapper = userMapper;
        this.userMapping = userMapping;
        this.privilegeMapper = privilegeMapper;
        this.jwt = jwt;
        this.expiration = expiration;
    }

    @SneakyThrows
    @Override
    @Transactional
    public void updateUserRequest(List<UserRegisterRequest> request){
        updateUser(userMapping.userRegisterRequestToUser(request));
    }
    @Transactional
    @Override
    public void updateUser(List<Users> usersRecords) {
        Set<String> existingUserNames = userMapper.findByExistingUserName(usersRecords, PageHelper.startPage(0, 1));
        List<Users> usersToInsert = new ArrayList<>();
        List<Users> usersToUpdate = new ArrayList<>();

        for(Users users : usersRecords) {
            if (existingUserNames.contains(users.getUserName())) {
                usersToUpdate.add(users);
            } else {
                usersToInsert.add(users);
            }
        }
        if(!usersToInsert.isEmpty()) {
            userMapper.insertUsers(usersToInsert);
        }
        if(!usersToUpdate.isEmpty()) {
            userMapper.updateUsers(usersToUpdate);
        }
    }

    @Override
    public Users findByFirstUserName(String username) {
        return userMapper.findByFirstUserName(username, new RowBounds(0,1));
    }

    @Override
    public Integer findByPrivilegeType(String username) {
        return userMapper.findByPrivilegeType(username, new RowBounds(0,1));
    }

    @Override
    public boolean existsByFirstUsername(String admin) {
        RowBounds rowbounds = new RowBounds(0,1);
        return userMapper.existsByUsername(admin, rowbounds) != null;
    }

    @Override
    public String login(String username, String password) {
        String userPassword = userMapper.findByFirstUserPassword(username,new RowBounds(0,1));
        if(userPassword == null){
            throw new IllegalArgumentException("User is not registered");
        }
        if(!password.equals(userPassword)){
            throw new IllegalArgumentException("Incorrect password");
        }
//        String token = (String) redisUtil.get("token."+user.getUserName());
//        if(token != null){
//            return token;
//        }
        String token = jwt.generateToken(username, userPassword);
//        redisUtil.set("token."+user.getUserName(), token, expiration, TimeUnit.SECONDS);
        return token;
    }
}