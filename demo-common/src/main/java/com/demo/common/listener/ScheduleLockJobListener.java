package com.demo.common.listener;

import com.demo.common.util.RedisUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobListener;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;
@Slf4j
public class ScheduleLockJobListener implements JobListener {
//    @Autowired
    private final RedissonClient redissonClient;
    private static final long heartbeatInterval_default = 30;
    public ScheduleLockJobListener(RedissonClient redissonClient) {
        this.redissonClient = redissonClient;
    }
    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @SneakyThrows
    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        JobDetail jobDetail = context.getJobDetail();
        String jobId = jobDetail.getKey().getName();
        long heartbeatInterval = (long) ObjectUtils.defaultIfNull(jobDetail.getJobDataMap().get("heartbeatInterval"), heartbeatInterval_default);
        RLock rLock = redissonClient.getLock(jobId);
        if (!rLock.tryLock(heartbeatInterval, TimeUnit.SECONDS)) {
            throw new JobExecutionException(jobId + " job is already running");
        }
    }

    @Override
    public void jobExecutionVetoed(JobExecutionContext context) {

    }

    @Override
    public void jobWasExecuted(JobExecutionContext context, JobExecutionException jobException) {
        String jobId = context.getJobDetail().getKey().getName();
        RLock rLock = redissonClient.getLock(jobId);
        rLock.unlock();
    }
}
