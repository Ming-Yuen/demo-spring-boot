package com.demo.admin.batch;

import com.demo.admin.dao.UsersPendingDao;
import com.demo.admin.entity.UserInfoPending;
import com.demo.admin.entity.enums.StatusEnum;
import com.demo.admin.service.UserService;
import com.demo.admin.listener.JobCompletionNotificationListener;
import com.demo.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.BindException;

import java.io.File;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
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
    private final String batchId = UUID.randomUUID().toString();
    @Bean
    public ItemReader<UserInfoPending> reader() {
        FlatFileItemReader<UserInfoPending> reader = new FlatFileItemReader<>();
//        reader.setResource(new ClassPathResource("C:\\Users\\Administrator\\Documents\\logs\\data.csv"));
        reader.setResource(new FileSystemResource(String.join(File.separator, System.getProperty("user.home"), "Documents", "Testing", "user_data.csv")));
        reader.setLineMapper(new DefaultLineMapper<UserInfoPending>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("username", "firstName", "lastName", "password", "email", "gender", "modifyTime");
            }});
//            setFieldSetMapper(new BeanWrapperFieldSetMapper<UserPending>() {{
//                setTargetType(UserPending.class);
//            }});
            setFieldSetMapper(new UserFieldSetMapper());
        }});
        reader.setLinesToSkip(1);
        return reader;
    }

    @Bean
    public UserItemProcessor processor() {
        return new UserItemProcessor();
    }

    @Bean
    public ItemWriter<UserInfoPending> writer() {
        return user->userService.saveUserPending(user);
    }

    @Bean
    public Step insertToPending(ItemReader<UserInfoPending> reader, ItemWriter<UserInfoPending> writer, UserItemProcessor processor) {
        return stepBuilderFactory.get("step1")
                .<UserInfoPending, UserInfoPending>chunk(10000)
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
                .start(mergeStep())
//                .next(mergeStep())
//                .next(mergeStep())
                .build();
    }

    @Bean
    public Step mergeStep() {
        return stepBuilderFactory.get("mergeStep")
                .tasklet((contribution, chunkContext) -> {
                    userService.confirmPendingUserInfo(batchId);
                    return RepeatStatus.FINISHED;
                })
                .build();
    }


    public static class UserItemProcessor implements ItemProcessor<UserInfoPending, UserInfoPending> {
        final AtomicInteger count = new AtomicInteger(0);
        final ConcurrentHashMap<String, UserInfoPending> map = new ConcurrentHashMap<>();
        @Override
        public UserInfoPending process(UserInfoPending userPending) throws Exception {
            final int process_count = count.addAndGet(1);
            if(process_count % 10000 == 0){
                log.info("read {} row", process_count);
            }

            UserInfoPending old_userPending = map.computeIfAbsent(userPending.getUserName(), record -> userPending);
            synchronized (old_userPending){
                if((old_userPending = map.get(userPending.getUserName())) == null){
                    map.put(userPending.getUserName(), userPending);
                    return userPending;
                }
            }
//            UserPending old_userPending = map.computeIfAbsent(userPending.getUserName(), record -> userPending);
//            if(old_userPending == null){
//                synchronized (map){
//                    if((old_userPending = map.get(userPending.getUserName())) == null){
//                        setUserPendingRecord(userPending);
//                        map.put(userPending.getUserName(), userPending);
//                        return userPending;
//                    }
//                }
//            }
            if(!old_userPending.getUpdatedAt().isBefore(userPending.getUpdatedAt())){
                return null;
            }
            old_userPending = map.get(userPending.getUserName());
            synchronized (old_userPending){
                if(old_userPending.getUpdatedAt().isBefore(userPending.getUpdatedAt())){
                    userPending.setVersion(old_userPending.getVersion() + 1);
                    map.put(userPending.getUserName(), userPending);
                    return userPending;
                }
            }
            return null;
        }
    }

    public class UserFieldSetMapper implements FieldSetMapper<UserInfoPending> {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        @Override
        public UserInfoPending mapFieldSet(FieldSet fieldSet) throws BindException {
            UserInfoPending userPending = new UserInfoPending();
            userPending.setBatchId(batchId);
            userPending.setUserName(fieldSet.readString("username"));
            userPending.setFirstName(fieldSet.readString("firstName"));
            userPending.setLastName(fieldSet.readString("lastName"));
            userPending.setPwd(fieldSet.readString("password"));
            userPending.setEmail(fieldSet.readString("email"));
            userPending.setGender(fieldSet.readString("gender"));
            userPending.setStatus(StatusEnum.PENDING);
            userPending.setCreatedBy("admin");
            userPending.setCreatedAt(OffsetDateTime.now());
            userPending.setUpdatedBy("admin");
            userPending.setUpdatedAt(DateUtil.convertOffsetDatetime("yyyy-MM-dd HH:mm:ss.SSS", fieldSet.readString("modifyTime")));
            userPending.setVersion(1);
            return userPending;
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