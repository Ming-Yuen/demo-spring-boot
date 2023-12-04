package com.demo.admin.listener;

import com.demo.common.entity.UserInfo;
import com.demo.common.entity.enums.UserRole;
import com.demo.admin.service.UserService;
import com.demo.common.service.ScheduleService;
import com.demo.common.util.ContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
@Slf4j
@Component
@Order(1)
public class DataListener implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;
    @Override
    public void run(String... args) throws Exception {
        UserInfo user = userService.findByUserName("admin");
        if(user == null){
            user = new UserInfo();
            user.setUserName("admin");
            user.setPwd(userService.passwordEncode("admin"));
            user.setRole(UserRole.admin);
            user.setCreatedBy(user.getUserName());
            user.setCreatedAt(OffsetDateTime.now());
            user.setUpdatedBy(user.getUserName());
            user.setUpdatedAt(OffsetDateTime.now());
            ContextHolder.setUser(user);

            userService.saveUser(user);
        }else{
            ContextHolder.setUser(user);
        }
        log.info("data init success");
    }
}