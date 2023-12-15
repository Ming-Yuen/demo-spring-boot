package com.demo.admin.service.impl;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.converter.UserConverter;
import com.demo.common.constant.RedisConstant;
import com.demo.admin.entity.UserInfo;
import com.demo.admin.service.UserService;
import com.demo.admin.dao.UserInfoDao;
import com.demo.admin.dao.UserRoleDao;
import com.demo.admin.security.JwtManager;
import com.demo.common.entity.BaseEntity;
import com.demo.common.entity.enums.UserRole;
import com.demo.common.util.Json;
import com.demo.common.util.LambdaUtil;
import com.demo.common.util.RedisUtil;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private UserConverter userMapper;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private JwtManager jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${jdbc.parameter_size}")
    private Integer batch_size;
    @SneakyThrows
    @Override
    public void register(List<UserRegisterRequest> request){

        log.info(Json.objectMapper.writeValueAsString(request));
        UserInfo[] userInfos = userMapper.userRegisterRequestToUser(request);
        log.info(Json.objectMapper.writeValueAsString(userInfos));
        saveUser(userMapper.userRegisterRequestToUser(request));
    }
    @SneakyThrows
    @Transactional
    @Override
    public void saveUser(UserInfo... userInfoRecords) {
        userInfoRecords = Arrays.stream(userInfoRecords).filter(LambdaUtil.distinctByKey(UserInfo::getUserName)).toArray(UserInfo[]::new);
        List<UserInfo> usersToInsert = new ArrayList<>();
        List<UserInfo> usersToUpdate = new ArrayList<>();

        Map<String, UserInfo> userInfoMap= findByUserName(Arrays.stream(userInfoRecords).map(UserInfo::getUserName).toArray(String[] :: new));
        for(UserInfo userInfo : userInfoRecords) {
            UserInfo old_userInfo = userInfoMap.get(userInfo.getUserName());
            if (old_userInfo == null) {
                usersToInsert.add(userInfo);
            } else {
                userInfo.setId(old_userInfo.getId());
                usersToUpdate.add(userInfo);
            }
        }
        userDao.peristAllAndFlush(usersToInsert);
        userDao.mergeAllAndFlush(usersToUpdate);
//        redisUtil.multiSet(usersToInsert, UserInfo::getUserName);
//        redisUtil.multiSet(usersToUpdate, UserInfo::getUserName);
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
        for(int index = 0; index < usernames.length; index+=batch_size){
            userDao.findByUserNameIn(Arrays.stream(usernames).skip(index).limit(batch_size).toArray(String[]::new)).forEach(userInfo -> {
                userInfoTempMap.put(userInfo.getUserName(), userInfo);
            });
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
        token = jwt.generateToken(user.getUserName(), user.getPassword());
        redisUtil.set("token."+user.getUserName(), token, expiration, TimeUnit.SECONDS);
        return token;
    }
    public boolean passwordMatch(String password, UserInfo admin){
        return passwordEncoder.matches(password, admin.getPassword());
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