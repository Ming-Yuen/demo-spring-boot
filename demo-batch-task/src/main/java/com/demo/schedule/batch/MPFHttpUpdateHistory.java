//package com.demo.schedule.batch;
//
//import com.demo.common.util.DateUtil;
//import com.demo.product.mapper.ProductMapper;
//import com.demo.product.mapper.ProductPriceMapper;
//import com.demo.product.vo.manulife.MPFDailyResponse;
//import com.demo.product.entity.Product;
//import com.demo.product.entity.ProductPrice;
//import com.demo.product.service.ProductService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.Step;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.job.builder.JobBuilder;
//import org.springframework.batch.core.launch.support.RunIdIncrementer;
//import org.springframework.batch.core.repository.JobRepository;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.batch.core.step.builder.StepBuilder;
//import org.springframework.batch.core.step.tasklet.Tasklet;
//import org.springframework.batch.repeat.RepeatStatus;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.PlatformTransactionManager;
//import org.springframework.web.client.RestTemplate;
//
//import java.io.File;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//import java.util.concurrent.ConcurrentHashMap;
//
//@Slf4j
//@Configuration
//@RequiredArgsConstructor
//public class MPFHttpUpdateHistory {
//    private final String task = this.getClass().getName();
//    private final JobRepository jobRepository;
//    private final PlatformTransactionManager transactionManager;
//    @Autowired
//    private ProductService productService;
//    @Autowired
//    private ProductMapper productMapper;
//    @Autowired
//    private ProductPriceMapper productPriceMapper;
//
//    @Bean
//    public Step downloadAndSaveStep(final JobRepository jobRepository, final PlatformTransactionManager transactionManager) {
//        return new StepBuilder("downloadAndSaveStep", jobRepository)
//                .tasklet(new MPFDownloadTasklet(),transactionManager)
//                .build();
//    }
//
//    @Bean
//    public Job downloadAndSaveJob(final JobRepository jobRepository,Step downloadAndSaveStep) {
//        return new JobBuilder("com.demo.product.batch.MPFHttpUpdateHistory",jobRepository)
//                .incrementer(new RunIdIncrementer())
//                .start(downloadAndSaveStep)
//                .build();
//    }
//
//    private class MPFDownloadTasklet implements Tasklet {
//        @Override
//        public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
//            ResponseEntity<String> response = new RestTemplate().exchange("https://www.manulife.com.hk/bin/funds/fundslist?productLine=mpf&overrideLocale=zh_Hant_HK", HttpMethod.GET, null, String.class);
//            if (response.getStatusCode() == HttpStatus.OK) {
//                String jsonResponse = response.getBody();
//                log.info("response : " + jsonResponse);
//
//                if(jsonResponse != null){
//                    ObjectMapper objectMapper = new ObjectMapper();
//                    objectMapper.registerModule(new JavaTimeModule());
//                    ConcurrentHashMap<String, Product> productMap = new ConcurrentHashMap<>();
//                    ConcurrentHashMap<String, ProductPrice> productPriceMap = new ConcurrentHashMap<>();
//                    List<MPFDailyResponse> records = objectMapper.readValue(jsonResponse, new TypeReference<List<MPFDailyResponse>>(){});
//                    if(!records.isEmpty()){
//
//                        records.forEach(item->{
//                            if(item.getNav().getAsOfDate() != null){
//                                return;
//                            }
//                            if(!productService.existsByProductId(item.getFundId())){
//                                Product product = productMapper.convert(item);
//                                productMap.put(item.getFundId(), product);
//                            }
//
//                            if(item.getNav() != null){
//                                ProductPrice productPrice = productService.getLatestProductPrice(DateUtil.convertOffsetDatetime(item.getNav().getAsOfDate()), item.getFundId(), "HK");
//                                if(productPrice == null){
//                                    ProductPrice price = productPriceMapper.convert(item);
//                                    productPriceMap.put(String.join(".", String.valueOf(item.getNav().getAsOfDate()), item.getFundId()), price);
//                                }
//                            }
//                        });
//                        productService.save(productMap.values().toArray(new Product[]{}));
//                        productService.save(productPriceMap.values().toArray(new ProductPrice[]{}));
//                        FileUtils.write(new File("mpf//manulife"+ records.get(0).getNav().getAsOfDate()+".txt"), jsonResponse, StandardCharsets.UTF_8);
//                    }
//                }
//            }
//            return RepeatStatus.FINISHED;
//        }
//    }
//}
