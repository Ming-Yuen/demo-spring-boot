//package com.demo.common.config;
//
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class RedissonConfig {
//    @Value("${spring.cache.type}")
//    private String type;
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private String port;
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redissonClient() {
//        Config config = new Config();
//        config.useSingleServer().setAddress(String.format("%s://%s:%s", type, host, port));
//        return Redisson.create(config);
//    }
//}
