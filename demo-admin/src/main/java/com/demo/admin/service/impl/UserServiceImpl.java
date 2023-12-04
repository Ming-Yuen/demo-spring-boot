package com.demo.admin.service.impl;

import com.demo.admin.UserMapper;
import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.common.constant.RedisConstant;
import com.demo.common.entity.UserInfo;
import com.demo.admin.service.UserService;
import com.demo.admin.dao.UserInfoDao;
import com.demo.admin.dao.UserRoleDao;
import com.demo.admin.util.JwtManager;
import com.demo.common.entity.BaseEntity;
import com.demo.common.entity.enums.UserRole;
import com.demo.common.util.RedisUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
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
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void register(List<UserRegisterRequest> request){
        saveUser(userMapper.userRegisterRequestToUser(request));
    }
    @Override
    public void saveUser(UserInfo... userInfoRecords) {
        List<UserInfo> usersToInsert = new ArrayList<>();
        List<UserInfo> usersToUpdate = new ArrayList<>();
        for(int index = 0; index < userInfoRecords.length; index+=100){
            String[] usernames = Arrays.stream(userInfoRecords).map(UserInfo::getUserName).skip(index).limit(100).toArray(String[] :: new);
            Map<String, UserInfo> userInfoMap= findByUserName(usernames);
            for(String username : userInfoMap.keySet()) {
                UserInfo userInfo = userInfoMap.get(username);
                if (findByUserName(userInfo.getUserName()) == null) {
                    usersToInsert.add(userInfo);
                    redisUtil.set(userInfo.getUserName(), userInfo);
                } else {
                    usersToUpdate.add(userInfo);
                }
            }
        }
        userDao.persistAll(usersToInsert);
        userDao.mergeAll(usersToUpdate);
//        redisUtil.multiSet(usersToInsert, UserInfo::getUserName);
        redisUtil.multiSet(usersToUpdate, UserInfo::getUserName);
    }
    @Override
    public UserInfo findByUserName(String username){
        return findByUserName(new String[]{username}).get(username);
    }

    @Override
    public Map<String, UserInfo> findByUserName(String... usernames){
        Map<String, UserInfo> userInfoMap = new HashMap<>();
        ArrayList<String> noCacheUser = new ArrayList<>();
        for(String username : usernames){
            UserInfo userInfo = (UserInfo) redisUtil.get(RedisConstant.userInfo);
            if(userInfo != null){
                userInfoMap.put(userInfo.getUserName(), userInfo);
            }else{
                noCacheUser.add(username);
            }
        }
        Map<String, UserInfo> userInfoTempMap = new HashMap<>();
        for(String username : noCacheUser) {
            UserInfo userInfo = userDao.findByUserName(username);
            if(userInfo != null){
                userInfoTempMap.put(userInfo.getUserName(), userInfo);
            }
        }
        if(!userInfoTempMap.isEmpty()){
            redisUtil.multiSet(userInfoTempMap);
        }
        userInfoMap.putAll(userInfoTempMap);
        return userInfoMap;
    }
    @Override
    public Collection<Long> getManageRoles(UserRole userRole){
        return userRoleDao.findByRoleLevelGreaterThanEqual(userRole).stream().map(BaseEntity::getId).collect(Collectors.toList());
    }
    @Value("${jwt.expiration}")
    private Long expiration;
    @Override
    public String login(String username, String password) {
        UserInfo user = userDao.findByUserName(username);
        if(user == null || !passwordMatch(password, user)){
            throw new IllegalArgumentException("Incorrect password");
        }
        String token = (String) redisUtil.get("token."+user.getUserName());
        if(token != null){
            return token;
        }
        token = jwt.generateToken(user.getUserName(), user.getPwd());
        redisUtil.set("token."+user.getUserName(), token, expiration, TimeUnit.SECONDS);
        return token;
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