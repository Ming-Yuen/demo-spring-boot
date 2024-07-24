package com.demo.batckTask.batch;

import com.demo.batckTask.constant.JobNames;
import com.demo.batckTask.dto.SalesImportFile;
import com.demo.batckTask.listener.JobCompletionNotificationListener;
import com.demo.batckTask.mapper.BatchTaskMapper;
import com.demo.batckTask.util.AggregateItemReader;
import com.demo.product.service.ProductService;
import com.demo.transaction.entity.SalesOrder;
import com.demo.transaction.entity.SalesOrderItem;
import com.demo.transaction.service.SalesService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.Chunk;
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

import java.beans.PropertyEditorSupport;
import java.io.File;
import java.util.*;

@Slf4j
@AllArgsConstructor
@Configuration
public class SalesFileImport {
    private BatchTaskMapper batchTaskMapper;
    private ProductService productService;
    private SalesService salesService;
    public static String filePath = String.join(File.separator, System.getProperty("user.home"), "Documents", "Testing", "sales_data.csv");
    @Bean
    public Step salesFileImportStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
        return new StepBuilder(JobNames.SALES_IMPORT+"Step", jobRepository)
                .<List<SalesImportFile>, List<SalesOrder>>chunk(100, transactionManager)
                .reader(new AggregateItemReader<SalesImportFile>(reader(), SalesImportFile::getOrderId))
                .processor(new SalesItemProcessor())
                .writer(new SalesItemWriter())
//                .taskExecutor(taskExecutor())
                .build();
    }
    @Bean
    public Job salesFileImportJob(final JobRepository jobRepository,Step insertToPending) {
        return new JobBuilder(JobNames.SALES_IMPORT, jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(new JobCompletionNotificationListener())
                .start(insertToPending)
                .build();
    }
    public ItemReader<SalesImportFile> reader() {
        FlatFileItemReader<SalesImportFile> reader = new FlatFileItemReader<>();
        reader.setResource(new FileSystemResource(filePath));

        Map<Class<?>, PropertyEditorSupport> customEditors = new HashMap<>();
//        customEditors.put(OffsetDateTime.class, new OffsetDateTimeEditor("yyyy-MM-dd HH:mm:ss.SSS"));
        reader.setLineMapper(new DefaultLineMapper<SalesImportFile>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("orderId","txDatetime","storeCode","customerName","salesPerson","productId","amount","qty","unitPrice");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<SalesImportFile>() {{
                setTargetType(SalesImportFile.class);
//                setCustomEditors();
            }});
        }});
        reader.setLinesToSkip(1);
        return reader;
    }

    public class SalesItemProcessor implements ItemProcessor<List<SalesImportFile>, List<SalesOrder>> {
        @Override
        public List<SalesOrder> process(List<SalesImportFile> salesImportFile) {
            return batchTaskMapper.convertFileFormat(salesImportFile);
        }
    }
    public class SalesItemWriter implements ItemWriter<List<SalesOrder>> {
        @Override
        public void write(Chunk<? extends List<SalesOrder>> chunk) {
            SalesOrder[] orders = chunk.getItems().stream().flatMap(Collection::stream).toArray(SalesOrder[]::new);
            SalesOrderItem[] salesOrderItems = Arrays.stream(orders).map(item->item.getItems()).flatMap(Collection::stream).toArray(SalesOrderItem[]::new);

            productService.update(batchTaskMapper.toProduct(salesOrderItems));
            productService.update(batchTaskMapper.toProductPrice(salesOrderItems));
            salesService.updateSales(orders);
        }
    }
}
