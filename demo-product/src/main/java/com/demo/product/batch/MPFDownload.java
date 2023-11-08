package com.demo.product.batch;

import com.demo.product.dto.manulife.MPFDailyResponse;
import com.demo.product.mapper.ProductMapper;
import com.demo.product.service.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Configuration
@EnableBatchProcessing
//@ConditionalOnProperty(name = "quartz.enabled", havingValue = "true", matchIfMissing = true)
public class MPFDownload {
    private final String task = this.getClass().getName();
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;
    @Autowired
    private ProductService productService;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private JobParameters jobParameters;

    @Bean
    public Step downloadAndSaveStep() {
        return stepBuilderFactory.get(task)
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

                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());
                List<MPFDailyResponse> entities = objectMapper.readValue(jsonResponse, new TypeReference<List<MPFDailyResponse>>(){});
            }
            return RepeatStatus.FINISHED;
        }
    }
}
