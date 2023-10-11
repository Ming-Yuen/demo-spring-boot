package com.demo.admin.service.impl;

import com.demo.admin.dao.UsersPendingDao;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.UserInfo;
import com.demo.admin.entity.UserPending;
import com.demo.admin.entity.enums.RoleLevelEnum;
import com.demo.admin.mapper.UsersPendingMapper;
import com.demo.admin.service.UserService;
import com.demo.admin.dao.UserInfoDao;
import com.demo.admin.dao.UserRoleDao;
import com.demo.admin.util.JwtManager;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.demo.common.util.LambdaUtil.distinctByKey;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserInfoDao userDao;
    @Autowired
    private UsersPendingDao usersPendingDao;
    @Autowired
    private UsersPendingMapper userMapper;
    @Autowired
    private UserRoleDao userRoleDao;
    @Autowired
    private JwtManager jwt;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public void register(List<UserRegisterRequest> request){
        List<UserPending> users = request.parallelStream().map(dto->{
            return userMapper.userRegisterRequestToUser(dto);
        }).collect(Collectors.toList());
        saveUserPending(users);
    }
    @Override
    public void saveUserPending(List<? extends UserPending> users){
        users = users.parallelStream()
                .filter(distinctByKey(UserPending::getUserName))
//                .map(user->{
//                        user.setPassword(passwordEncoder.encode(user.getPassword()));
//                        return user;
//                    })
                .collect(Collectors.toList());
        usersPendingDao.saveAll(users);
    }

    @Override
    public void confirmUserPending(List<? extends UserPending> users){

    }
    @Override
    public UserInfo findByUserName(String username){
        return userDao.findByUserName(username);
    }
    @Override
    @Cacheable()
    public Collection<Long> getManageRoles(RoleLevelEnum role_level){
        return userRoleDao.findByRoleLevelGreaterThanEqual(role_level).stream().map(x->x.getId()).collect(Collectors.toList());
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
}
