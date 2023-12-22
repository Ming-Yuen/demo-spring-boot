package com.demo.product.batch;

import com.demo.product.dto.manulife.MPFDailyResponse;
import com.demo.product.entity.Product;
import com.demo.product.entity.ProductPrice;
import com.demo.product.mapper.ProductMapper;
import com.demo.product.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@EnableBatchProcessing
@Configuration
public class MPFDownload {
    private final String task = this.getClass().getName();
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;

    @Bean
    public Step downloadAndSaveStep() {
        return stepBuilderFactory.get("downloadAndSaveJob")
                .tasklet(new MPFDownloadTasklet())
                .build();
    }

    @Bean
    public Job downloadAndSaveJob(Step downloadAndSaveStep) {
        return jobBuilderFactory.get(task)
                .incrementer(new RunIdIncrementer())
                .start(downloadAndSaveStep)
                .build();
    }

    private class MPFDownloadTasklet implements Tasklet {
        @Override
        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
            ResponseEntity<String> response = new RestTemplate().exchange("https://www.manulife.com.hk/bin/funds/fundslist?productLine=mpf&overrideLocale=zh_Hant_HK", HttpMethod.GET, null, String.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                String jsonResponse = response.getBody();
                log.info("response : " + jsonResponse);

                if(jsonResponse != null){
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    ConcurrentHashMap<String, Product> productMap = new ConcurrentHashMap<>();
                    ConcurrentHashMap<String, ProductPrice> productPriceMap = new ConcurrentHashMap<>();
                    objectMapper.readValue(jsonResponse, new TypeReference<List<MPFDailyResponse>>(){})
                            .forEach(item->{
                                if(!productService.existsByProductId(item.getFundId())){
                                    Product product = new Product();
                                    product.setProductId(item.getFundId());
                                    product.setRegion("HK");
                                    product.setName(item.getFundName());
                                    product.setCategory(item.getPlatformName());
                                    productMap.put(item.getFundId(), product);
                                }

                                if(item.getNav() != null){
                                    ProductPrice productPrice = productService.getLatestProductPrice(item.getNav().getAsOfDate(), item.getFundId(), "HK");
                                    if(productPrice == null){
                                        ProductPrice price = getProductPrice(item);
                                        productPriceMap.put(String.join(".", String.valueOf(item.getNav().getAsOfDate()), item.getFundId()), price);
                                    }
                                }
                            });
                    productService.save(productMap.values().toArray(new Product[]{}));
                    productService.save(productPriceMap.values().toArray(new ProductPrice[]{}));
                    FileUtils.write(new File("mpf//manulife"+ LocalDate.now().toString()+".txt"), jsonResponse, StandardCharsets.UTF_8);
                }
            }
            return RepeatStatus.FINISHED;
        }

        private ProductPrice getProductPrice(MPFDailyResponse item) {
            ProductPrice price = new ProductPrice();
            price.setRegion("HK");
            price.setCurrencyUnit("HKD");
            price.setProductId(item.getFundId());
            price.setEffectiveDate(item.getNav().getAsOfDate());
            price.setPrice(item.getNav().getPrice());
            return price;
        }
    }
}
