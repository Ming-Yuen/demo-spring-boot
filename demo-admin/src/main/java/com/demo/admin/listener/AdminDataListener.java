package com.demo.admin.listener;

import com.demo.admin.entity.User;
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
        User user = userService.findByUserName("admin");
        if(user == null){
            user = new User();
            user.setUserName("admin");
            user.setPassword("admin");
            user.setRole("admin");
            user.setCreatedBy(user.getUserName());
            user.setCreatedAt(OffsetDateTime.now());
            user.setUpdatedBy(user.getUserName());
            user.setUpdatedAt(OffsetDateTime.now());

            userService.saveUserEncryptPassword(user);
        }
    }
}