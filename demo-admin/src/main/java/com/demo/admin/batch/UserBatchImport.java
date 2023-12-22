package com.demo.admin.batch;

import com.demo.admin.enums.Gender;
import com.demo.admin.service.UserService;
import com.demo.admin.listener.JobCompletionNotificationListener;
import com.demo.admin.entity.UserInfo;
import com.demo.common.entity.enums.UserRole;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.io.File;
import java.time.OffsetDateTime;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@EnableBatchProcessing
public class UserBatchImport {
    private final String task = this.getClass().getName();
    @Autowired
    public JobBuilderFactory jobBuilderFactory;
    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    public UserService userService;
    @Bean
    public ItemReader<UserInfo> reader() {
        FlatFileItemReader<UserInfo> reader = new FlatFileItemReader<>();
//        reader.setResource(new ClassPathResource("C:\\Users\\Administrator\\Documents\\logs\\data.csv"));
        reader.setResource(new FileSystemResource(String.join(File.separator, System.getProperty("user.home"), "Documents", "Testing", "user_data.csv")));
        reader.setLineMapper(new DefaultLineMapper<UserInfo>() {{
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
    public ItemWriter<UserInfo> writer() {
        return users->userService.saveUserEncryptPassword(users.toArray(new UserInfo[]{}));
    }

    @Bean
    public Step insertToPending(ItemReader<UserInfo> reader, ItemWriter<UserInfo> writer, UserItemProcessor processor) {
        return stepBuilderFactory.get("step1")
                .<UserInfo, UserInfo>chunk(10000)
                .reader(reader)
                .processor(processor)
                .writer(writer)
//                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job importUserJob(Step insertToPending) {
        return jobBuilderFactory.get(task)
                .incrementer(new RunIdIncrementer())
                .listener(new JobCompletionNotificationListener())
                .start(insertToPending)
                .build();
    }

    public static class UserItemProcessor implements ItemProcessor<UserInfo, UserInfo> {
        final AtomicInteger count = new AtomicInteger(0);
        @Override
        public UserInfo process(UserInfo user) throws Exception {
            final int process_count = count.addAndGet(1);
            if(process_count % 10000 == 0){
                log.info("read {} row", process_count);
            }
            return user;
//            UserInfo old_userPending = map.get(user.getUserName());
//            if(old_userPending == null){
//                synchronized (map){
//                    if((old_userPending = map.get(user.getUserName())) == null){
//                        map.put(user.getUserName(), user);
//                        return user;
//                    }
//                }
//            }
//            if(user.getUpdatedAt().compareTo(old_userPending.getUpdatedAt()) > 0){
//                synchronized (old_userPending){
//                    if(user.getUpdatedAt().compareTo(old_userPending.getUpdatedAt()) > 0){
//                        user.setId(old_userPending.getId());
//                        user.setVersion(old_userPending.getVersion() + 1);
//                        map.put(user.getUserName(), user);
//                        return user;
//                    }
//                }
//            }
//            return null;
        }
    }

    public class UserFieldSetMapper implements FieldSetMapper<UserInfo> {
        @Override
        public UserInfo mapFieldSet(FieldSet fieldSet) {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserName(fieldSet.readString("username"));
            userInfo.setFirstName(fieldSet.readString("firstName"));
            userInfo.setLastName(fieldSet.readString("lastName"));
            userInfo.setPassword(fieldSet.readString("password"));
            userInfo.setEmail(fieldSet.readString("email"));
            userInfo.setGender(Gender.fromString(fieldSet.readString("gender")));
            userInfo.setCreatedBy("admin");
            userInfo.setCreatedAt(OffsetDateTime.now());
            userInfo.setUpdatedBy("admin");
            userInfo.setUpdatedAt(DateUtil.convertOffsetDatetime("yyyy-MM-dd HH:mm:ss.SSS", fieldSet.readString("modifyTime")));
            userInfo.setRole(UserRole.user);
            return userInfo;
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