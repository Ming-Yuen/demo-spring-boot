package com.demo.admin.service;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.common.entity.UserInfo;
import com.demo.common.entity.enums.RoleLevelEnum;

import java.util.Collection;
import java.util.List;

public interface UserService {

    void register(List<UserRegisterRequest> user);
    String login(String username, String password);
    UserInfo findByUserName(String userId);
    Collection<Long> getManageRoles(RoleLevelEnum role_level);

    String passwordEncode(String password);
    List<UserInfo> query(UserQueryRequest request);
    void saveUser(UserInfo... userInfoRecords);
}
