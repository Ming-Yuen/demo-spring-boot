package com.demo.schedule.listener;

import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.schedule.schedule.MPFDailyDownloadScheduler;
import com.demo.schedule.entity.Schedule;
import com.demo.schedule.service.ScheduleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
@Slf4j
@Component
@Order(1)
public class ScheduleDataListener {
    @Autowired
    private ScheduleService scheduleService;
    @PostConstruct
    public void init() {
        Schedule schedule = scheduleService.findByName("Import_MPF_History");
        if(schedule == null) {
            ScheduleUpdateRequest request = new ScheduleUpdateRequest();
            request.setName("Import_MPF_History");
            request.setDescription("Import MPF history");
            request.setJobClass(MPFDailyDownloadScheduler.class.getName());
            request.setCron("*/5 * * * * ? *");
            request.setEnable(1);
            scheduleService.update(request);
        }
        log.info("data init success");
    }
}