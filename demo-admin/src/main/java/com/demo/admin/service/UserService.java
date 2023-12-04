package com.demo.admin.service;

import com.demo.admin.dto.UserQueryRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.common.entity.UserInfo;
import com.demo.common.entity.enums.RoleLevelEnum;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface UserService {

    void register(List<UserRegisterRequest> user);
    String login(String username, String password);
    Map<String, UserInfo> findByUserName(String... usernames);
    UserInfo findByUserName(String username);
    Collection<Long> getManageRoles(RoleLevelEnum role_level);

    String passwordEncode(String password);
    List<UserInfo> query(UserQueryRequest request);
    @Transactional
    void saveUser(UserInfo... userInfoRecords);
}
