package com.demo.admin.service.impl;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.mapper.UserConverter;
import com.demo.admin.entity.User;
import com.demo.admin.service.UserService;
import com.demo.admin.dao.UserDao;
import com.demo.admin.dao.UserRoleDao;
import com.demo.admin.security.JwtManager;
import com.demo.common.entity.BaseEntity;
import com.demo.common.entity.enums.UserRole;
import com.demo.common.util.Json;
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
        User[] user = userMapper.userRegisterRequestToUser(request);
        saveUserEncryptPassword(user);
    }
    @Transactional
    public void saveUserEncryptPassword(User... userRecords) {
        if(userRecords == null){
            return;
        }
        Arrays.stream(userRecords).forEach(userInfo -> {
            userInfo.setPassword(userInfo.getPassword());
        });
        saveUser(userRecords);
    }
    @Transactional
    @Override
    public void saveUser(User... userRecords) {
        userRecords = Arrays.stream(userRecords).filter(LambdaUtil.distinctByKey(User::getUserName)).toArray(User[]::new);
        List<User> usersToInsert = new ArrayList<>();
        List<User> usersToUpdate = new ArrayList<>();

        Map<String, User> userInfoMap= findByUserName(Arrays.stream(userRecords).map(User::getUserName).toArray(String[] :: new));
        for(User user : userRecords) {
            User old_user = userInfoMap.get(user.getUserName());
            if (old_user == null) {
                usersToInsert.add(user);
                entityManager.persist(user);
            } else {
                user.setId(old_user.getId());
                usersToUpdate.add(user);
                entityManager.merge(user);
            }
        }
    }
    @Override
    public User findByUserName(String username){
        return findByUserName(new String[]{username}).get(username);
    }

    @Override
    public Map<String, User> findByUserName(String... usernames){
        Map<String, User> userInfoMap = new HashMap<>();
        ArrayList<String> noCacheUser = new ArrayList<>();
        for(String username : usernames){
//            UserInfo userInfo = (UserInfo) redisUtil.get(RedisConstant.userInfo);
//            if(userInfo != null){
//                userInfoMap.put(userInfo.getUserName(), userInfo);
//            }else{
                noCacheUser.add(username);
//            }
        }
        Map<String, User> userInfoTempMap = new HashMap<>();
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
        User user = userDao.findByUserName(username);
        if(user == null){
            throw new IllegalArgumentException("User is not registered");
        }
        if(!password.equals(user.getPassword())){
            throw new IllegalArgumentException("Incorrect password");
        }
//        String token = (String) redisUtil.get("token."+user.getUserName());
//        if(token != null){
//            return token;
//        }
        String token = jwt.generateToken(user.getUserName(), user.getPassword());
//        redisUtil.set("token."+user.getUserName(), token, expiration, TimeUnit.SECONDS);
        return token;
    }
    @Override
    public List<User> query(UserQueryRequest request) {
        return userDao.findByUserNameIn(request.getUserNameList());
    }
}