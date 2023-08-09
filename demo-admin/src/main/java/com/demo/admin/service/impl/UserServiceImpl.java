package com.demo.admin.service.impl;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.service.UserService;
import com.demo.common.dao.UserDao;
import com.demo.common.dao.UserRoleDao;
import com.demo.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
            user.setRoleId(UserDto.getRoleId());
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
    public Collection<Long> getManageRoles(Long id){
        Integer role_Level = userRoleDao.findByUid(id).getRoleLevel();
        return userRoleDao.findByRoleLevelGreaterThanEqual(role_Level).stream().map(x->x.getUid()).collect(Collectors.toList());
    }
}
