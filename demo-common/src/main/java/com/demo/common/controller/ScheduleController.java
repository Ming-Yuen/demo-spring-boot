package com.demo.common.controller;

import com.demo.common.controller.dto.DefaultResponse;
import com.demo.common.dto.ScheduleUpdateRequest;
import com.demo.common.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerPath.schedule)
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @PostMapping(path = ControllerPath.update)
    public void update(ScheduleUpdateRequest scheduleUpdateRequest){
        scheduleService.update(scheduleUpdateRequest);
    }
}
