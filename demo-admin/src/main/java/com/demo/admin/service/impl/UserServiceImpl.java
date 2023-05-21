package com.demo.admin.service.impl;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.service.UserService;
import com.demo.common.dao.UserDao;
import com.demo.common.dao.UserRoleDao;
import com.demo.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserRoleDao userRoleDao;

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
            user.setUserRole(UserDto.getRole());
            user.setCreator(UserDto.getCreator());
            user.setModifier(UserDto.getModifier());
            user.setModification_time(new Timestamp(System.currentTimeMillis()));
            user.setTx_creation_time(new Timestamp(UserDto.getTx_creation_time().getTime()));
            user.setTx_modification_time(new Timestamp(UserDto.getTx_modification_time().getTime()));
            return user;
        }).collect(Collectors.toList());
        userDao.saveAll(userRecords);
        return userRecords;
    }
    @Override
    public User findByUsername(String username){
        return userDao.findByUsername(username);
    }
    @Override
    @Cacheable()
    public Collection<String> getUserRoles(Integer role_Level){
        return userRoleDao.findByLessThanRoleLevel(role_Level).stream().map(x->x.getUserRole()).collect(Collectors.toList());
    }
}
