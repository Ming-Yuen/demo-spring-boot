package com.demo.schedule.controller;

import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.schedule.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerPath.schedule)
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @PostMapping(path = ControllerPath.UPDATE)
    public void update(ScheduleUpdateRequest scheduleUpdateRequest){
        scheduleService.update(scheduleUpdateRequest);
    }
}
