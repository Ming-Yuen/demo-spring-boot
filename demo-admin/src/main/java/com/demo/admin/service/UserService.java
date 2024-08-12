package com.demo.admin.service;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.Users;

import java.util.List;

public interface UserService {

    void updateUserRequest(List<UserRegisterRequest> user);
    String login(String username, String password);
    void updateUser(List<Users> usersRecords);

    Users findByFirstUserName(String username);

    Integer findByPrivilegeType(String username);

    boolean existsByFirstUsername(String admin);
}
