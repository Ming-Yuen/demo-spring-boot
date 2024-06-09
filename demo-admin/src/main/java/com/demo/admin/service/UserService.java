package com.demo.admin.service;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.UserInfo;
import com.demo.common.entity.enums.UserRole;

import java.util.Collection;
import java.util.List;

public interface UserService {

    void register(List<UserRegisterRequest> user);
    String login(String username, String password);

    <T> List<T> findByUserName(Class<T> type, String... usernames);

    Collection<Long> getManageRoles(UserRole userRole);

    List<UserInfo> query(UserQueryRequest request);
    void saveUserEncryptPassword(UserInfo... userInfoRecords);
    void saveUser(UserInfo... userInfoRecords);
}
