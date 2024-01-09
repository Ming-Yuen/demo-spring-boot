//package com.demo.product.batch;
//
//import com.demo.product.converter.ProductConverter;
//import com.demo.product.converter.ProductPriceConverter;
//import com.demo.product.dto.manulife.MPFDailyResponse;
//import com.demo.product.entity.Product;
//import com.demo.product.entity.ProductPrice;
//import com.demo.product.service.ProductService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.item.ItemReader;
//import org.springframework.batch.item.ItemWriter;
//import org.springframework.batch.item.file.FlatFileItemReader;
//import org.springframework.batch.item.file.mapping.DefaultLineMapper;
//import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
//import org.springframework.batch.item.support.IteratorItemReader;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.io.FileSystemResource;
//import org.springframework.core.io.support.ResourcePatternResolver;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.stream.Collectors;
//
//@Slf4j
//@EnableBatchProcessing
//@Configuration
//public class MPFFileUpdateHistory {
//    private final String task = this.getClass().getName();
//    @Autowired
//    private JobBuilderFactory jobBuilderFactory;
//    @Autowired
//    private StepBuilderFactory stepBuilderFactory;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private ProductConverter productConverter;
//    @Autowired
//    private ProductPriceConverter productPriceConverter;
//
//    @Bean
//    public ItemReader<MPFDailyResponse> reader() {
//        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//        Resource[] resources;
//        try {
//            resources = resolver.getResources("file:/path/to/json/files/*.json");
//        } catch (IOException e) {
//            throw new RuntimeException("Error reading JSON files from directory", e);
//        }
//
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        return new IteratorItemReader<>(Arrays.stream(resources)
//                .map(resource -> {
//                    try {
//                        return objectMapper.readValue(resource.getInputStream(), JsonData.class);
//                    } catch (IOException e) {
//                        throw new RuntimeException("Error reading JSON file: " + resource.getFilename(), e);
//                    }
//                })
//                .collect(Collectors.toList()));
//    }
//}
