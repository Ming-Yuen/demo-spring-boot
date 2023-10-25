package com.demo.admin.service;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.UserInfo;
import com.demo.admin.entity.UserPending;
import com.demo.admin.entity.enums.RoleLevelEnum;
import org.springframework.cache.annotation.Cacheable;

import java.util.Collection;
import java.util.List;


public interface UserService {

    void register(List<UserRegisterRequest> user);
    String login(String username, String password);

    void saveUserPending(List<? extends UserPending> users);

    void confirmPendingUserInfo(String uuid);

    void confirmUserPending(List<? extends UserPending> users);

    UserInfo findByUserName(String userId);
    @Cacheable()
    Collection<Long> getManageRoles(RoleLevelEnum role_level);

    String passwordEncode(String password);
}
