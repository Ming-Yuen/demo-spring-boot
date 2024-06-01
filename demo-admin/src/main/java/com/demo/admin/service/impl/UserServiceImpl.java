package com.demo.admin.service.impl;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.UserInfo;
import com.demo.admin.mapper.UserConverter;
import com.demo.admin.service.UserService;
import com.demo.admin.dao.UserDao;
import com.demo.admin.dao.UserRoleDao;
import com.demo.admin.security.JwtManager;
import com.demo.common.entity.BaseEntity;
import com.demo.common.entity.enums.UserRole;
import com.demo.common.util.LambdaUtil;
//import com.demo.common.util.RedisUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserConverter userMapper;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private JwtManager jwt;
    @PersistenceContext
    private EntityManager entityManager;
    @SneakyThrows
    @Override
    public void register(List<UserRegisterRequest> request){
        UserInfo[] userInfo = userMapper.userRegisterRequestToUser(request);
        saveUserEncryptPassword(userInfo);
    }
    @Transactional
    public void saveUserEncryptPassword(UserInfo... userInfoRecords) {
        if(userInfoRecords == null){
            return;
        }
        Arrays.stream(userInfoRecords).forEach(userInfo -> {
            userInfo.setUserPwd(userInfo.getUserPwd());
        });
        saveUser(userInfoRecords);
    }
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
                entityManager.persist(userInfo);
            } else {
                userInfo.setId(old_userInfo.getId());
                usersToUpdate.add(userInfo);
                entityManager.merge(userInfo);
            }
        }
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
//            UserInfo userInfo = (UserInfo) redisUtil.get(RedisConstant.userInfo);
//            if(userInfo != null){
//                userInfoMap.put(userInfo.getUserName(), userInfo);
//            }else{
                noCacheUser.add(username);
//            }
        }
        Map<String, UserInfo> userInfoTempMap = new HashMap<>();
        userDao.findByUserNameIn(noCacheUser.toArray(String[]::new)).forEach(userInfo -> {
            userInfoTempMap.put(userInfo.getUserName(), userInfo);
        });
//        if(!userInfoTempMap.isEmpty()){
//            redisUtil.multiSet(userInfoTempMap);
//        }
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
        UserInfo userInfo = userDao.findByUserName(username);
        if(userInfo == null){
            throw new IllegalArgumentException("User is not registered");
        }
        if(!password.equals(userInfo.getUserPwd())){
            throw new IllegalArgumentException("Incorrect password");
        }
//        String token = (String) redisUtil.get("token."+user.getUserName());
//        if(token != null){
//            return token;
//        }
        String token = jwt.generateToken(userInfo.getUserName(), userInfo.getUserPwd());
//        redisUtil.set("token."+user.getUserName(), token, expiration, TimeUnit.SECONDS);
        return token;
    }
    @Override
    public List<UserInfo> query(UserQueryRequest request) {
        return userDao.findByUserNameIn(request.getUserNameList());
    }
}