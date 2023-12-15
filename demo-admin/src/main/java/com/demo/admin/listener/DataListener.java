package com.demo.admin.listener;

import com.demo.admin.schedule.CsvToUserScheduler;
import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.common.entity.Schedule;
import com.demo.admin.entity.UserInfo;
import com.demo.common.entity.enums.UserRole;
import com.demo.admin.service.UserService;
import com.demo.common.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.OffsetDateTime;
@Slf4j
@Component
@Order(1)
public class DataListener {//implements CommandLineRunner {
    @Autowired
    private UserService userService;
    @Autowired
    private ScheduleService scheduleService;
//    @Override
//    public void run(String... args) {
    @PostConstruct
    public void init() {
        UserInfo user = userService.findByUserName("admin");
        if(user == null){
            user = new UserInfo();
            user.setUserName("admin");
            user.setPassword(userService.passwordEncode("admin"));
            user.setRole(UserRole.admin);
            user.setCreatedBy(user.getUserName());
            user.setCreatedAt(OffsetDateTime.now());
            user.setUpdatedBy(user.getUserName());
            user.setUpdatedAt(OffsetDateTime.now());

            userService.saveUser(user);
        }
        Schedule schedule = scheduleService.findByName("Import_User_info_from_csv_file");
        if(schedule == null) {
            ScheduleUpdateRequest request = new ScheduleUpdateRequest();
            request.setName("Import_User_info_from_csv_file");
            request.setDescription("Read csv file import data to user_info");
            request.setJobClass(CsvToUserScheduler.class.getName());
            request.setCron("*/5 * * * * ? *");
            request.setEnable(1);
            scheduleService.update(request);
        }
        log.info("data init success");
    }
}