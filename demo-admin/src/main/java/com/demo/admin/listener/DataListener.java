package com.demo.admin.listener;

import com.demo.admin.schedule.CsvToUserScheduler;
import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.common.entity.Schedule;
import com.demo.common.entity.UserInfo;
import com.demo.admin.entity.UserInfoPending;
import com.demo.common.entity.enums.RoleLevelEnum;
import com.demo.admin.entity.enums.StatusEnum;
import com.demo.admin.mapper.UsersPendingMapper;
import com.demo.admin.service.UserService;
import com.demo.common.service.ScheduleService;
import com.demo.common.util.ContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.UUID;
@Slf4j
@Component
@Order(1)
public class DataListener implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private UsersPendingMapper usersPendingMapper;
    @Autowired
    private ScheduleService scheduleService;
    @Override
    public void run(String... args) throws Exception {
        UserInfo user = userService.findByUserName("admin");
        if(user == null){
            UserInfoPending userPending = new UserInfoPending();
            userPending.setBatchId(UUID.randomUUID().toString());
            userPending.setUserName("admin");
            userPending.setPwd(userService.passwordEncode("admin"));
            userPending.setRoleLevel(RoleLevelEnum.admin);
            userPending.setStatus(StatusEnum.PENDING);
            userPending.setCreatedBy(userPending.getUserName());
            userPending.setCreatedAt(OffsetDateTime.now());
            userPending.setUpdatedBy(userPending.getUserName());
            userPending.setUpdatedAt(OffsetDateTime.now());

            user = usersPendingMapper.convertToUserInfo(userPending);
            ContextHolder.setUser(user);

            userService.saveUserPending(Collections.singletonList(userPending));
            userService.confirmPendingUserInfo(userPending.getBatchId());
        }else{
            ContextHolder.setUser(user);
        }
        log.info("data init success");
    }
}