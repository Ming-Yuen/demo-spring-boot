package com.demo.admin.listener;

import com.demo.admin.entity.UserInfo;
import com.demo.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.List;

@Slf4j
@Component
public class AdminDataListener implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        List<UserInfo.SelectUserName> userInfoList = userService.findByUserName(UserInfo.SelectUserName.class,"admin");
        if(CollectionUtils.isEmpty(userInfoList)){
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName("admin");
            userInfo.setFirstName("temp");
            userInfo.setLastName("temp");
            userInfo.setUserPassword("admin");
            userInfo.setPrivilege("admin");
            userInfo.setGender("none");
            userInfo.setCreatedBy(userInfo.getUserName());
            userInfo.setCreatedAt(OffsetDateTime.now());
            userInfo.setUpdatedBy(userInfo.getUserName());
            userInfo.setUpdatedAt(OffsetDateTime.now());

            userService.updateUserMaster(userInfo);
        }
    }
}