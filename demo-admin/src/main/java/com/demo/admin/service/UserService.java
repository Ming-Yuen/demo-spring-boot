package com.demo.admin.service;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.User;
import com.demo.common.entity.enums.UserRole;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserService {

    void register(List<UserRegisterRequest> user);
    String login(String username, String password);
    Map<String, User> findByUserName(String... usernames);
    User findByUserName(String username);
    Collection<Long> getManageRoles(UserRole userRole);

    List<User> query(UserQueryRequest request);
    void saveUserEncryptPassword(User... userRecords);
    void saveUser(User... userRecords);
}
