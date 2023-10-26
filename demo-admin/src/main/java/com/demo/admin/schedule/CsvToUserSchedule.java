package com.demo.admin.schedule;

import com.demo.common.exception.ValidationException;
import com.github.javafaker.Faker;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CsvToUserSchedule implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        File file = new File(String.join(File.separator, System.getProperty("user.home"), "Documents", "Testing"),"user_data.csv");
        try {
            generateDataFile(file);
        } catch (Exception e) {
            throw new RuntimeException(e);
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

            for (int i = 1; i <= 1000000; i++) {
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