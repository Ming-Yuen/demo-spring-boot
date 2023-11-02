package com.demo.admin.service;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.common.entity.UserInfo;
import com.demo.admin.entity.UserInfoPending;
import com.demo.common.entity.enums.RoleLevelEnum;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collection;
import java.util.List;


public interface UserService {

    void register(List<UserRegisterRequest> user);
    String login(String username, String password);

    void saveUserPending(List<? extends UserInfoPending> users);

    void confirmPendingUserInfo(String uuid);

    void confirmUserPending(List<? extends UserInfoPending> users);

    UserInfo findByUserName(String userId);
    Collection<Long> getManageRoles(RoleLevelEnum role_level);

    String passwordEncode(String password);
    List<UserInfo> query(UserQueryRequest request);
}
