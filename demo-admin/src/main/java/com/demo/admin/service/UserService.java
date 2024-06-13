package com.demo.admin.service;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.UserInfo;
import com.demo.common.entity.enums.UserRole;

import java.util.Collection;
import java.util.List;

public interface UserService {

    void updateUserRequest(List<UserRegisterRequest> user);
    String login(String username, String password);

    <T> List<T> findByUserName(Class<T> type, String... usernames);

    Collection<Long> getManageRoles(UserRole userRole);

    List<UserInfo> userQueryRequest(UserQueryRequest request);
    void updateUserMaster(UserInfo... userInfoRecords);
}
