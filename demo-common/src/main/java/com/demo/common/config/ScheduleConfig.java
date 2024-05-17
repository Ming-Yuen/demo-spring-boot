package com.demo.common.config;

import com.demo.common.entity.Schedule;
import com.demo.common.exception.ValidationException;
import com.demo.common.listener.ScheduleLockJobListener;
import com.demo.common.service.ScheduleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
@Slf4j
@Component
@Order(2)
public class ScheduleConfig implements ApplicationRunner {
    @Value("${quartz.enabled}")
    private boolean quartzEnable;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private AutowiredSpringBeanJobFactory autowiredSpringBeanJobFactory;
    @Autowired
    private RedissonClient redissonClient;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        if(quartzEnable) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            ObjectMapper objectMapper = new ObjectMapper();
            boolean scheduleIsEmpty = true;
            List<Schedule> schedules = scheduleService.getAllSchedule();
            log.info("Found a total of {} schedules", schedules.size());
            for (Schedule schedule : schedules) {
                try {
                    Class<?> jobClass = null;
                    try {
                        jobClass = Class.forName(schedule.getJobClass());
                    } catch (ClassNotFoundException exception) {
                        throw new ValidationException("Job id " + schedule.getId(), exception);
                    }
                    if (!Job.class.isAssignableFrom(jobClass)) {
                        throw new ValidationException("Job id " + schedule.getId() + " job class is not Quartz Job sub interface class");
                    }
                    Map<String, String> scheduleConfig = null;
                    if (schedule.getConfig() != null) {
                        try {
                            scheduleConfig = objectMapper.readValue(schedule.getConfig(), new TypeReference<Map<String, String>>() {});
                        } catch (JsonProcessingException e) {
                            throw new ValidationException("Job id " + schedule.getId() + " is not json value");
                        }
                    }
                    @SuppressWarnings("unchecked")
                    JobBuilder jobBuilder = JobBuilder.newJob((Class<? extends Job>) jobClass)
                            .withIdentity(String.valueOf(schedule.getId()), "group1");
                    if (scheduleConfig != null) {
                        jobBuilder.usingJobData(new JobDataMap(scheduleConfig));
                    }
                    JobDetail jobDetail = jobBuilder.build();
                    Trigger trigger1 = TriggerBuilder.newTrigger()
                            .withIdentity(String.valueOf(schedule.getId()), "group1")
                            .withSchedule(CronScheduleBuilder.cronSchedule(schedule.getCron()))
                            .build();
                    scheduler.scheduleJob(jobDetail, trigger1);
                    scheduleIsEmpty = false;
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
            if (!scheduleIsEmpty) {
                scheduler.start();
            }
            log.info("Total {} schedule start", scheduler.getCurrentlyExecutingJobs().size());
        }
    }
    @Bean
    public Scheduler scheduler() throws SchedulerException {
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        scheduler.setJobFactory(autowiredSpringBeanJobFactory);
        scheduler.getListenerManager().addJobListener(new ScheduleLockJobListener(redissonClient));
        scheduler.start();
        return scheduler;
    }
}
