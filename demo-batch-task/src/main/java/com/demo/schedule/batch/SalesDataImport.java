package com.demo.schedule.batch;

import com.demo.admin.entity.UserInfo;
import com.demo.schedule.dto.SalesFileRecord;
import com.demo.transaction.entity.SalesOrder;
import com.demo.transaction.service.SalesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Configuration
@AllArgsConstructor
public class SalesDataImport {
    private SalesService salesService;
    @Bean
    public Step downloadAndSaveStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder("downloadAndSaveStep", jobRepository)
                .<SalesFileRecord, SalesOrder>chunk(1000, transactionManager)
                .reader(reader())
                .processor(new SalesItemProcessor())
                .writer(writer())
                .build();
    }

    public ItemReader<SalesFileRecord> reader() {
        FlatFileItemReader<SalesFileRecord> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(String.join(File.separator, System.getProperty("user.home"), "Documents", "Testing", "sales_data.csv")));
        reader.setLineMapper(new DefaultLineMapper<SalesFileRecord>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("username", "firstName", "lastName", "password", "email", "gender", "modifyTime");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<SalesFileRecord>() {{
                setTargetType(SalesFileRecord.class);
            }});
//            setFieldSetMapper(new UserBatchImport.UserFieldSetMapper());
        }});
        reader.setLinesToSkip(1);
        return reader;
    }

    public static class SalesItemProcessor implements ItemProcessor<SalesFileRecord, SalesOrder> {
        final AtomicInteger count = new AtomicInteger(0);
        @Override
        public SalesOrder process(SalesFileRecord salesFileRecord) throws Exception {
            final int process_count = count.addAndGet(1);
            if(process_count % 10000 == 0){
                log.info("read {} row", process_count);
            }
            return null;
        }
    }
    public ItemWriter<SalesOrder> writer() {
        return salesOrders->salesService.updateSales(salesOrders.getItems().toArray(new SalesOrder[]{}));
    }
}
