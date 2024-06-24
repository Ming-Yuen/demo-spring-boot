package com.demo.schedule.config;

import com.demo.common.config.AutowiredSpringBeanJobFactory;
import com.demo.common.exception.ValidationException;
import com.demo.schedule.entity.BatchTask;
import com.demo.schedule.service.BatchTaskService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
@Slf4j
@Component
public class ScheduleConfig implements CommandLineRunner {
    @Value("${quartz.enabled}")
    private boolean quartzEnable;
    @Autowired
    private BatchTaskService batchTaskService;
    @Autowired
    private AutowiredSpringBeanJobFactory autowiredSpringBeanJobFactory;
    @Override
    public void run(String... args) throws Exception {
        log.info("Quartz schedule config by {}", quartzEnable);
        if(quartzEnable) {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            ObjectMapper objectMapper = new ObjectMapper();
            boolean scheduleIsEmpty = true;
            List<BatchTask> batchTasks = batchTaskService.getAllBatchTask();
            log.info("Found a total of {} schedules", batchTasks.size());
            for (BatchTask batchTask : batchTasks) {
                try {
                    Class<?> jobClass = null;
                    try {
                        jobClass = Class.forName(batchTask.getJobClass());
                    } catch (ClassNotFoundException exception) {
                        throw new ValidationException("Job id " + batchTask.getId(), exception);
                    }
                    if (!Job.class.isAssignableFrom(jobClass)) {
                        throw new ValidationException("Job id " + batchTask.getId() + " job class is not Quartz Job sub interface class");
                    }
                    Map<String, String> scheduleConfig = null;
                    if (batchTask.getConfig() != null) {
                        try {
                            scheduleConfig = objectMapper.readValue(batchTask.getConfig(), new TypeReference<Map<String, String>>() {});
                        } catch (JsonProcessingException e) {
                            throw new ValidationException("Job id " + batchTask.getId() + " is not json value");
                        }
                    }
                    @SuppressWarnings("unchecked")
                    JobBuilder jobBuilder = JobBuilder.newJob((Class<? extends Job>) jobClass)
                            .withIdentity(String.valueOf(batchTask.getId()), "group1");
                    if (scheduleConfig != null) {
                        jobBuilder.usingJobData(new JobDataMap(scheduleConfig));
                    }
                    JobDetail jobDetail = jobBuilder.build();
                    Trigger trigger1 = TriggerBuilder.newTrigger()
                            .withIdentity(String.valueOf(batchTask.getId()), "group1")
                            .withSchedule(CronScheduleBuilder.cronSchedule(batchTask.getCron()))
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
//        scheduler.getListenerManager().addJobListener(new ScheduleLockJobListener(redissonClient));
        scheduler.start();
        return scheduler;
    }
}
