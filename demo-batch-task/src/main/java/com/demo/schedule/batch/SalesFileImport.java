package com.demo.schedule.batch;

import com.demo.schedule.constant.JobNames;
import com.demo.schedule.dto.SalesImportFile;
import com.demo.schedule.listener.JobCompletionNotificationListener;
import com.demo.transaction.entity.SalesOrder;
import com.demo.transaction.service.SalesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.File;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@AllArgsConstructor
@Configuration
public class SalesFileImport {
    private SalesService salesService;
    @Bean
    public Step insertToPending(final JobRepository jobRepository, final PlatformTransactionManager transactionManager, ItemReader<SalesImportFile> reader, ItemWriter<SalesImportFile> writer) {
        return new StepBuilder(JobNames.SALES_IMPORT, jobRepository)
                .<SalesImportFile, SalesOrder>chunk(1000, transactionManager)
                .reader(reader())
                .processor(new UserItemProcessor())
                .writer(writer())
//                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public Job importUserJob(final JobRepository jobRepository,Step insertToPending) {
        return new JobBuilder(JobNames.SALES_IMPORT, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(new JobCompletionNotificationListener())
                .start(insertToPending)
                .build();
    }
    public ItemReader<SalesImportFile> reader() {
        FlatFileItemReader<SalesImportFile> reader = new FlatFileItemReader<>();
//        reader.setResource(new ClassPathResource("C:\\Users\\Administrator\\Documents\\logs\\data.csv"));
        reader.setResource(new FileSystemResource(String.join(File.separator, System.getProperty("user.home"), "Documents", "Testing", "user_data.csv")));
        reader.setLineMapper(new DefaultLineMapper<SalesImportFile>() {{
            setFieldSetMapper(new BeanWrapperFieldSetMapper<SalesImportFile>() {{
                setTargetType(SalesImportFile.class);
            }});
        }});
        reader.setLinesToSkip(1);
        return reader;
    }
    public ItemWriter<SalesOrder> writer() {
        return users->salesService.updateSales(users.getItems().toArray(new SalesOrder[]{}));
    }

    public static class UserItemProcessor implements ItemProcessor<SalesImportFile, SalesOrder> {
        final AtomicInteger count = new AtomicInteger(0);
        @Override
        public SalesOrder process(SalesImportFile userInfo) throws Exception {
            final int process_count = count.addAndGet(1);
            if(process_count % 10000 == 0){
                log.info("read {} row", process_count);
            }
            return null;
        }
    }
}
