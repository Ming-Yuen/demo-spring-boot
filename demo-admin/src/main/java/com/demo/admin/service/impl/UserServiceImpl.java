package com.demo.admin.service.impl;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.UserInfo;
import com.demo.admin.mapper.UserConverter;
import com.demo.admin.service.UserService;
import com.demo.admin.repository.UserRepository;
import com.demo.admin.repository.UserRoleRepository;
import com.demo.admin.security.JwtUtil;
import com.demo.common.entity.BaseEntity;
import com.demo.common.entity.enums.UserRole;
import com.demo.common.util.LambdaUtil;
//import com.demo.common.util.RedisUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private UserConverter userMapper;
    private UserRoleRepository userRoleRepository;
    private JwtUtil jwt;
    @PersistenceContext
    private EntityManager entityManager;
    private Long expiration;

    public UserServiceImpl(UserRepository userRepository, UserConverter userMapper, UserRoleRepository userRoleRepository, JwtUtil jwt, @Value("${jwt.expiration}") Long expiration) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userRoleRepository = userRoleRepository;
        this.jwt = jwt;
        this.expiration = expiration;
    }

    @SneakyThrows
    @Override
    @Transactional
    public void updateUserRequest(List<UserRegisterRequest> request){
        UserInfo[] userInfo = userMapper.userRegisterRequestToUser(request.toArray(new UserRegisterRequest[]{}));
        updateUserMaster(userInfo);
    }
    @Transactional
    @Override
    public void updateUserMaster(UserInfo... userInfoRecords) {
        userInfoRecords = Arrays.stream(userInfoRecords).filter(LambdaUtil.distinctByKey(UserInfo::getUserName)).toArray(UserInfo[]::new);
        List<UserInfo> usersToInsert = new ArrayList<>();
        List<UserInfo> usersToUpdate = new ArrayList<>();

        List<UserInfo.SelectUserName> userNameList = findByUserName(UserInfo.SelectUserName.class, Arrays.stream(userInfoRecords).map(UserInfo::getUserName).toArray(String[] :: new));
        Map<String, UserInfo.SelectUserName> userInfoMap = userNameList == null ? null : userNameList.stream().collect(Collectors.toMap(UserInfo.SelectUserName::userName, Function.identity()));
        for(UserInfo userInfo : userInfoRecords) {
            UserInfo.SelectUserName old_userInfo = userInfoMap == null ? null : userInfoMap.get(userInfo.getUserName());
            if (old_userInfo == null) {
                usersToInsert.add(userInfo);
                entityManager.persist(userInfo);
            } else {
                userInfo.setId(old_userInfo.id());
                usersToUpdate.add(userInfo);
                entityManager.merge(userInfo);
            }
        }
    }

    @Override
    public <T> List<T> findByUserName(Class<T> type, String... usernames){
        return userRepository.findByUserNameIn(type, usernames);
    }
    @Override
    public Collection<Long> getManageRoles(UserRole userRole){
        return userRoleRepository.findByRoleLevelGreaterThanEqual(userRole).stream().map(BaseEntity::getId).collect(Collectors.toList());
    }
    @Override
    public String login(String username, String password) {
        List<UserInfo.SelectUserPwd> userInfoList = findByUserName(UserInfo.SelectUserPwd.class, username);
        if(userInfoList.isEmpty()){
            throw new IllegalArgumentException("User is not registered");
        }
        UserInfo.SelectUserPwd userInfo = userInfoList.get(0);
        if(!password.equals(userInfo.userPwd())){
            throw new IllegalArgumentException("Incorrect password");
        }
//        String token = (String) redisUtil.get("token."+user.getUserName());
//        if(token != null){
//            return token;
//        }
        String token = jwt.generateToken(userInfo.userName(), userInfo.userPwd());
//        redisUtil.set("token."+user.getUserName(), token, expiration, TimeUnit.SECONDS);
        return token;
    }
    @Override
    public List<UserInfo> userQueryRequest(UserQueryRequest request) {
        return findByUserName(UserInfo.class, request.getUserNameList());
    }
}