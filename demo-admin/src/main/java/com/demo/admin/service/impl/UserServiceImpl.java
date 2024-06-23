package com.demo.admin.service.impl;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.Privilege;
import com.demo.admin.entity.UserInfo;
import com.demo.admin.mapper.UserMapper;
import com.demo.admin.service.UserService;
import com.demo.admin.repository.UserRepository;
import com.demo.admin.repository.PrivilegeRepository;
import com.demo.admin.security.JwtUtil;
import com.demo.admin.enums.PrivilegeType;
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
    private UserMapper userMapper;
    private PrivilegeRepository privilegeRepository;
    private JwtUtil jwt;
    @PersistenceContext
    private EntityManager entityManager;
    private Long expiration;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, PrivilegeRepository privilegeRepository, JwtUtil jwt, @Value("${jwt.expiration}") Long expiration) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.privilegeRepository = privilegeRepository;
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
    public List<PrivilegeType> getSubPrivilege(PrivilegeType... privilegeTypes){
        Set<PrivilegeType> privilegeList = new HashSet<PrivilegeType>();
        List<Privilege.SelectSubPrivilege> selectSubPrivilegesList = privilegeRepository.findByPrivilegeIn(Privilege.SelectSubPrivilege.class, privilegeTypes);

        privilegeList.addAll(selectSubPrivilegesList.stream().map(x->x.subPrivileges()).collect(Collectors.toSet()));

        return privilegeList.stream().toList();
    }
    @Override
    public String login(String username, String password) {
        List<UserInfo.SelectUserPassword> userInfoList = findByUserName(UserInfo.SelectUserPassword.class, username);
        if(userInfoList.isEmpty()){
            throw new IllegalArgumentException("User is not registered");
        }
        UserInfo.SelectUserPassword userInfo = userInfoList.get(0);
        if(!password.equals(userInfo.userPassword())){
            throw new IllegalArgumentException("Incorrect password");
        }
//        String token = (String) redisUtil.get("token."+user.getUserName());
//        if(token != null){
//            return token;
//        }
        String token = jwt.generateToken(userInfo.userName(), userInfo.userPassword());
//        redisUtil.set("token."+user.getUserName(), token, expiration, TimeUnit.SECONDS);
        return token;
    }
    @Override
    public List<UserInfo> userQueryRequest(UserQueryRequest request) {
        return findByUserName(UserInfo.class, request.getUserNameList());
    }
}