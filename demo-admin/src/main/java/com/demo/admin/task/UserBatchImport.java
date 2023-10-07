package com.demo.admin.task;

import com.demo.admin.dao.UsersPendingDao;
import com.demo.admin.entity.UserPending;
import com.demo.admin.entity.enums.StatusEnum;
import com.demo.admin.service.UserService;
import com.demo.admin.listener.JobCompletionNotificationListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@EnableBatchProcessing
public class UserBatchImport {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public UserService userService;
    @Autowired
    public UsersPendingDao usersPendingDao;
    @Bean
    public ItemReader<UserPending> reader() {
        FlatFileItemReader<UserPending> reader = new FlatFileItemReader<>();
//        reader.setResource(new ClassPathResource("C:\\Users\\Administrator\\Documents\\logs\\data.csv"));
        reader.setResource(new FileSystemResource("C:\\Users\\Administrator\\Documents\\logs\\data.csv"));
        reader.setLineMapper(new DefaultLineMapper<UserPending>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("username", "firstName", "lastName", "password", "email", "gender", "modifyTime");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<UserPending>() {{
                setTargetType(UserPending.class);
            }});
        }});
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public UserItemProcessor processor() {
        return new UserItemProcessor();
    }

    @Bean
    public ItemWriter<UserPending> writer() {
        return user->userService.saveUserPending(user);
    }

    @Bean
    public Step insertToPending(ItemReader<UserPending> reader, ItemWriter<UserPending> writer, UserItemProcessor processor) {
        return stepBuilderFactory.get("step1")
                .<UserPending, UserPending>chunk(10000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job importUserJob(Step insertToPending) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobCompletionNotificationListener())
                .start(insertToPending)
//                .next(mergeStep())
                .build();
    }

    @Bean
    public Step mergeStep() {
        return stepBuilderFactory.get("mergeStep")
                .tasklet((contribution, chunkContext) -> {
                    usersPendingDao.confirmPendingUser();
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    public class UserItemProcessor implements ItemProcessor<UserPending, UserPending> {
        AtomicInteger count = new AtomicInteger(0);
        ConcurrentHashMap<String, UserPending> map = new ConcurrentHashMap();
        @Override
        public UserPending process(UserPending userPending) throws Exception {
            if(count.get() % 10000 == 0){
                log.info("read {} row", count);
            }
            count.addAndGet(1);

            UserPending old_userPending = map.get(userPending.getUserName());
            if(old_userPending == null){
                synchronized (map){
                    if(old_userPending == null){
                        setUserPendingRecord(userPending);
                        map.put(userPending.getUserName(), userPending);
                        return userPending;
                    }
                }
            }
            if(old_userPending.getModification_time().compareTo(userPending.getModification_time()) >= 0){
                return null;
            }
            old_userPending = map.get(userPending.getUserName());
            synchronized (old_userPending){
                if(old_userPending.getModification_time().compareTo(userPending.getModification_time()) < 0){
                    userPending.setTxVersion(old_userPending.getTxVersion() + 1);
                    map.put(userPending.getUserName(), userPending);
                    return userPending;
                }
            }
            return null;
        }

        private void setUserPendingRecord(UserPending userPending){
            userPending.setStatus(StatusEnum.PENDING);
            userPending.setTxVersion(1);
            userPending.setCreator("admin");
            userPending.setCreation_time(OffsetDateTime.now());
            userPending.setModifier("admin");
            userPending.setModification_time(OffsetDateTime.now());
        }
    }

    @Bean
    public TaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4); // 设置核心线程池大小
        executor.setMaxPoolSize(4); // 设置最大线程池大小
        executor.setQueueCapacity(25); // 设置队列容量
        executor.setThreadNamePrefix(this.getClass().getName()); // 设置线程名称前缀
        executor.initialize();
        return executor;
    }
}