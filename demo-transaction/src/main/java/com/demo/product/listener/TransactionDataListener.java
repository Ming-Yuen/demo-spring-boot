package com.demo.product.listener;

import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.common.entity.Schedule;
import com.demo.common.service.ScheduleService;
import com.demo.product.schedule.MPFDailyDownloadScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
@Slf4j
@Component
@Order(1)
public class TransactionDataListener {
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