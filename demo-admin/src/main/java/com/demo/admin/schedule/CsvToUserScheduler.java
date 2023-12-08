package com.demo.admin.schedule;

import com.demo.admin.batch.UserBatchImport;
import com.demo.common.exception.ValidationException;
import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
@Slf4j
@Configuration
public class CsvToUserScheduler extends QuartzJobBean {
    @Autowired
    private Job importUserJob;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private UserBatchImport userBatchImport;
    @Override
    protected void executeInternal(JobExecutionContext context) {
        File file = new File(String.join(File.separator, System.getProperty("user.home"), "Documents", "Testing"),"user_data.csv");
        if(!file.exists()) {
            try {
                generateDataFile(file);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try {
            log.info("start of schedule");
            JobParameters jobParameters= new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters();
            JobExecution result = jobLauncher.run(importUserJob, jobParameters);
            log.info("end of schedule");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public void generateDataFile(File file) throws ValidationException, IOException {
        Faker faker = new Faker(new Locale("en"));
        String lineSeparator = System.lineSeparator();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()){
            throw new ValidationException("create directory fail " + file.getParentFile().getPath());
        }
        try (FileWriter writer = new FileWriter(file)) {
            // Write header
            writer.append("username,firstName,lastName,password,email,gender,modifyTime");
            writer.append(lineSeparator);

            for (int i = 1; i <= 100000000; i++) {
                String username = faker.name().username();
                String firstName = faker.name().firstName();
                String lastName = faker.name().lastName();
                String password = faker.internet().password();
                String email = faker.internet().emailAddress();
                String gender = faker.demographic().sex();
                String modifyTime = dateFormat.format(new Date());

                // Write record
                writer.append(String.format("%s,%s,%s,%s,%s,%s,%s",
                        username, firstName, lastName, password, email, gender, modifyTime));
                writer.append(lineSeparator);
            }

            System.out.println("CSV file generated successfully!");
        }
    }
}
