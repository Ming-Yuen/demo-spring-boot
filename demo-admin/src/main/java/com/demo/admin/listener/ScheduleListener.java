package com.demo.admin.listener;

import com.demo.common.entity.Schedule;
import com.demo.common.exception.ValidationException;
import com.demo.common.service.ScheduleService;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ScheduleListener {
    @Autowired
    private ScheduleService scheduleService;
    public void config() throws ValidationException, SchedulerException {
        Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
        for(Schedule schedule : scheduleService.getAllSchedule()){
            Class<?> jobClass = null;
            try {
                jobClass = Class.forName(schedule.getJobClass());
            }catch(ClassNotFoundException exception){
                throw new ValidationException("Job id " + schedule.getId(), exception);
            }
            if(!jobClass.isAssignableFrom(Job.class)){
                throw new ValidationException("Job id " + schedule.getId() + " job class is not Quartz Job sub interface class");
            }
            @SuppressWarnings("unchecked")
            JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) jobClass).withIdentity(String.valueOf(schedule.getId()), "group1").build();
            Trigger trigger1 = TriggerBuilder.newTrigger().withIdentity(String.valueOf(schedule.getId()), "group1").withSchedule(CronScheduleBuilder.cronSchedule(schedule.getCron())).build();
            scheduler.scheduleJob(jobDetail, trigger1);
        }
        scheduler.start();
    }
}
