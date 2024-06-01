package com.demo.admin.listener;

import com.demo.admin.entity.UserInfo;
import com.demo.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
@Slf4j
@Component
public class AdminDataListener implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        UserInfo userInfo = userService.findByUserName("admin");
        if(userInfo == null){
            userInfo = new UserInfo();
            userInfo.setUserName("admin");
            userInfo.setFirstName("temp");
            userInfo.setLastName("temp");
            userInfo.setUserPwd("admin");
            userInfo.setRole("admin");
            userInfo.setGender("none");
            userInfo.setCreatedBy(userInfo.getUserName());
            userInfo.setCreatedAt(OffsetDateTime.now());
            userInfo.setUpdatedBy(userInfo.getUserName());
            userInfo.setUpdatedAt(OffsetDateTime.now());

            userService.saveUserEncryptPassword(userInfo);
        }
    }
}