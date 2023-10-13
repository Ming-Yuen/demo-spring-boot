package com.demo.admin.task.testing;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class UserBatchImportTest {
    private final String docPath = String.join(File.separator, System.getProperty("user.home"), "Documents", "Testing");;
    @Test
    public void generateDataFile() throws Throwable{
        File csvFile = new File(docPath, "user_data.csv");
        Faker faker = new Faker(new Locale("en"));
        String lineSeparator = System.lineSeparator();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if(!csvFile.getParentFile().exists() && !csvFile.getParentFile().mkdirs()){
            throw new Exception("create directory fail " + csvFile.getParentFile().getPath());
        }
        try (FileWriter writer = new FileWriter(csvFile)) {
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
