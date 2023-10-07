package com.demo.admin.listener;

import com.demo.admin.entity.UserInfo;
import com.demo.admin.entity.UserPending;
import com.demo.admin.entity.enums.RoleLevelEnum;
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
            userPending.setUserName("admin");
            userPending.setPwd("admin");
            userPending.setRoleLevel(RoleLevelEnum.admin);
            userPending.setCreator(userPending.getUserName());
            userPending.setCreation_time(OffsetDateTime.now());
            userPending.setModifier(userPending.getUserName());
            userPending.setModification_time(OffsetDateTime.now());

            user = usersPendingMapper.userPendingConvertUserInfo(userPending);
            UserContextHolder.setUser(user);

            userService.saveUserPending(Arrays.asList(userPending));
        }else{
            UserContextHolder.setUser(user);
        }
    }
}