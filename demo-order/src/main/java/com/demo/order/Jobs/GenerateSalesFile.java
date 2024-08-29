package com.demo.order.Jobs;

import com.demo.order.Jobs.SalesFileImport;
import com.demo.common.exception.ValidationException;
import com.github.javafaker.Faker;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class GenerateSalesFile {
    public static void main(String[] args) throws ValidationException, IOException {
        generateDataFile(new File(SalesFileImport.filePath));
    }
    public static void generateDataFile(File file) throws ValidationException, IOException {
        Faker faker = new Faker(new Locale("en"));
        String lineSeparator = System.lineSeparator();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        if(!file.getParentFile().exists() && !file.getParentFile().mkdirs()){
            throw new ValidationException("create directory fail " + file.getParentFile().getPath());
        }
        try (OutputStreamWriter writer = new OutputStreamWriter(Files.newOutputStream(file.toPath()), StandardCharsets.UTF_8)) {
            // Write header
            writer.append("orderId,txDatetime,storeCode,customerName,salesPerson,productId,amount,qty,unitPrice,discountMethod,discount");
            writer.append(lineSeparator);

            Random random = new Random();

            for (int i = 1; i <= 10000; i++) {

                int randomNumber = random.nextInt(100);

                String orderId = faker.number().digits(10);
                String txDatetime = dateFormat.format(faker.date().past(3650, TimeUnit.DAYS));
                String storeCode = faker.bothify("STR-#####");
                String customerName = faker.name().fullName();
                String salesPerson = faker.name().fullName();

                for(int item = 0; item < randomNumber; item++){
                    String productId = faker.number().digits(6);
                    Integer qty = faker.number().numberBetween(1, 100);
                    String unitPrice = faker.commerce().price(1.0, 100.0);
                    BigDecimal amount = new BigDecimal(unitPrice).multiply(BigDecimal.valueOf(qty));

                    // Write record
                    writer.append(String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",
                            orderId, txDatetime, storeCode, customerName, salesPerson, productId, amount, qty, unitPrice));
                    writer.append(lineSeparator);
                }
            }

            System.out.println("CSV file generated successfully!");
        }
    }
}
