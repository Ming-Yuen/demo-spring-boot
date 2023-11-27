package com.demo.admin.service.impl;

import com.demo.admin.UserMapper;
import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.common.entity.UserInfo;
import com.demo.common.entity.enums.RoleLevelEnum;
import com.demo.admin.service.UserService;
import com.demo.admin.dao.UserInfoDao;
import com.demo.admin.dao.UserRoleDao;
import com.demo.admin.util.JwtManager;
import com.demo.common.entity.BaseEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private JPAQueryFactory queryFactory;
    @Autowired
    private UserInfoDao userDao;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private JwtManager jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void register(List<UserRegisterRequest> request){
        UserInfo[] users = request.parallelStream().map(dto->{
            return userMapper.userRegisterRequestToUser(dto);
        }).toArray(UserInfo[] :: new);
        saveUser(users);
    }
    @Override
    public void saveUser(UserInfo... userInfo) {

    }

//    @Cacheable(value = "userInfoCache", key = "#userName", condition="#userName!=null")
    @Cacheable("userInfoCache.userName")
    @Override
    public UserInfo findByUserName(String username){
        return userDao.findByUserName(username);
    }
    @Override
    public Collection<Long> getManageRoles(RoleLevelEnum role_level){
        return userRoleDao.findByRoleLevelGreaterThanEqual(role_level).stream().map(BaseEntity::getId).collect(Collectors.toList());
    }
    @Override
    public String login(String username, String password) {
        UserInfo user = userDao.findByUserName(username);
        if(user == null || !passwordMatch(password, user)){
            throw new IllegalArgumentException("Incorrect password");
        }

        return jwt.generateToken(user.getUserName(), user.getPwd());
    }
    public boolean passwordMatch(String password, UserInfo admin){
        return passwordEncoder.matches(password, admin.getPwd());
    }
    @Override
    public String passwordEncode(String password){
        return passwordEncoder.encode(password);
    }
    @Override
    public List<UserInfo> query(UserQueryRequest request) {
        return userDao.findByUserNameIn(request.getUserNameList());
    }
}