package com.demo.admin.service.impl;

import com.demo.admin.dao.UserDao;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.User;
import com.demo.admin.service.UserService;
import com.demo.common.constant.RedisConstant;
import com.demo.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RedisService redis;

    @Value("${admin.adminId}")
    private String adminId;

    @Value("${admin.default.password}")
    private String adminDefaultPassword;

    @Value("${admin.default.emailAddress}")
    private String adminDefaultEmailAddress;
    @Override
    public User adminRegistration(){
        User admin = userDao.findByUsername(adminId);
        if(admin == null){
            admin = new User();
            admin.setEmail(adminDefaultEmailAddress);
            admin.setPassword(adminDefaultPassword);
            admin.setGender("M");
            admin.setUsername(adminId);
            admin.setRole("admin");
            admin.setCreator(adminId);
            admin.setModifier(adminId);
            admin.setCreation_time(new Timestamp(System.currentTimeMillis()));
            admin.setModification_time(new Timestamp(System.currentTimeMillis()));
            admin.setTx_creation_time(new Timestamp(System.currentTimeMillis()));
            admin.setTx_modification_time(new Timestamp(System.currentTimeMillis()));
            userDao.save(admin);
        }
        return admin;
    }

    @Override
    public List<User> update(List<UserRegisterRequest> request){
        List<User> userRecords = request.stream().map(UserDto->{
            User user = findByUsername(UserDto.getUsername());
            if(findByUsername(UserDto.getCreator()) == null){
                throw new IllegalArgumentException(String.format("User[%s] creator[%s] not registered", UserDto.getUsername(), UserDto.getCreator()));
            }
            user = user == null ? new User() : user;
            user.setUsername(UserDto.getUsername());
            user.setFirstName(UserDto.getFirstName());
            user.setLastName(UserDto.getLastName());
            user.setPassword(UserDto.getPassword());
            user.setGender(UserDto.getGender());
            user.setEmail(UserDto.getEmail());
            user.setPhone(UserDto.getPhone());
            user.setRole(UserDto.getRole());
            user.setCreator(UserDto.getCreator());
            user.setModifier(UserDto.getModifier());
            user.setModification_time(new Timestamp(System.currentTimeMillis()));
            user.setTx_creation_time(new Timestamp(UserDto.getTx_creation_time().getTime()));
            user.setTx_modification_time(new Timestamp(UserDto.getTx_modification_time().getTime()));
            return user;
        }).collect(Collectors.toList());
        userDao.saveAll(userRecords);
        redis.<User>set(x->RedisConstant.user_name_getUser + x.getUsername(), userRecords);
        return userRecords;
    }
    @Override
    public User findByUsername(String username){
        User user = (User) redis.get(RedisConstant.user_name_getUser + username);
        if(user == null){
            user = userDao.findByUsername(username);
            redis.set(RedisConstant.user_name_getUser + username, user);
        }
        return user;
    }
}
