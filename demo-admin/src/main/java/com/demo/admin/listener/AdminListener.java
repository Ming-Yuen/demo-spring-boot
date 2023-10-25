package com.demo.admin.listener;

import com.demo.admin.entity.UserInfo;
import com.demo.admin.entity.UserPending;
import com.demo.admin.entity.enums.RoleLevelEnum;
import com.demo.admin.entity.enums.StatusEnum;
import com.demo.admin.mapper.UsersPendingMapper;
import com.demo.admin.service.UserService;
import com.demo.admin.util.UserContextHolder;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

@Slf4j
@Component
@Order(-1)
public class AdminListener implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersPendingMapper usersPendingMapper;
    @Override
    public void run(String... args) throws Exception {
        UserInfo user = userService.findByUserName("admin");
        if(user == null){
            UserPending userPending = new UserPending();
            userPending.setBatchId(UUID.randomUUID().toString());
            userPending.setUserName("admin");
            userPending.setPwd(userService.passwordEncode("admin"));
            userPending.setRoleLevel(RoleLevelEnum.admin);
            userPending.setStatus(StatusEnum.PENDING);
            userPending.setCreator(userPending.getUserName());
            userPending.setCreationTime(OffsetDateTime.now());
            userPending.setModifier(userPending.getUserName());
            userPending.setModificationTime(OffsetDateTime.now());

            user = usersPendingMapper.userPendingConvertUserInfo(userPending);
            UserContextHolder.setUser(user);

            userService.saveUserPending(Collections.singletonList(userPending));
            userService.confirmPendingUserInfo(userPending.getBatchId());
        }else{
            UserContextHolder.setUser(user);
        }
    }
}