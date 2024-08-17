package com.demo.common.config;

import com.demo.common.interceptor.BaseEntityAuditInterceptor;
import com.demo.common.interceptor.SqlExecutionTimeInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MyBatisConfig {

    @Bean
    public ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return configuration -> {
            configuration.addInterceptor(new BaseEntityAuditInterceptor());
            configuration.addInterceptor(sqlExecutionTimeInterceptor());
        };
    }
    @Bean
    public SqlExecutionTimeInterceptor sqlExecutionTimeInterceptor() {
        return new SqlExecutionTimeInterceptor();
    }
}
