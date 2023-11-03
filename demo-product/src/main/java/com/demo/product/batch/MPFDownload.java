package com.demo.product.batch;

import com.demo.product.entity.Product;
import com.demo.product.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.UrlResource;

import java.net.MalformedURLException;
import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
@ConditionalOnProperty(name = "quartz.enabled", havingValue = "true", matchIfMissing = true)
public class MPFDownload {
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ProductService productService;
    @Bean
    public Step downloadAndSaveStep(ItemReader<String> urlReader, ItemWriter<String> databaseWriter) {
        return stepBuilderFactory.get("downloadAndSaveStep")
                .<String, String>chunk(10)
                .reader(urlReader)
                .processor(item -> item) // Optional: Process the data if needed
                .writer(databaseWriter)
                .build();
    }
    @Bean
    public ItemWriter<Product> writer() {
        return products->productService.save((List<Product>) products);
    }
    @Bean
    public ItemReader<String> urlReader() throws MalformedURLException {
        return new FlatFileItemReaderBuilder<String>()
                .resource(new UrlResource("https://www.manulife.com.hk/bin/funds/fundslist?productLine=mpf&overrideLocale=zh_Hant_HK"))
                .lineMapper((line, lineNumber) -> line)
                .build();
    }
    @Bean
    public Job downloadAndSaveJob(Step downloadAndSaveStep) {
        return jobBuilderFactory.get("downloadAndSaveJob")
                .incrementer(new RunIdIncrementer())
                .start(downloadAndSaveStep)
                .build();
    }
}
