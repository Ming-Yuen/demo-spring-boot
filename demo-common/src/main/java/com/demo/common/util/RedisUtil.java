//package com.demo.common.util;
//
//import lombok.extern.slf4j.Slf4j;
////import org.redisson.api.RedissonClient;
//import org.springframework.beans.factory.annotation.Autowired;
////import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//import java.util.function.Function;
//@Slf4j
//@Component
//public class RedisUtil {
//
////    @Autowired
////    private RedisTemplate<String, Object> redisTemplate;
////    @Autowired
////    private RedissonClient redissonClient;
//
//    public void set(String key, Object value) {
////        redisTemplate.opsForValue().set(key, value);
//    }
//    public void set(String key, Object value, Long timeout, TimeUnit timeUnit) {
////        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
//    }
//    public <T> void multiSet(List<T> list, Function<T, String> key) {
////        Map<String, Object> map = new HashMap<>();
////        for(T record : list){
////            map.put(key.apply(record), record);
////        }
////        redisTemplate.opsForValue().multiSet(map);
//    }
//
//    public <T> void multiSet(Map<String, T> map){
////        redisTemplate.opsForValue().multiSet(map);
//    }
//
//    public Object get(String key) {
//        return null;//redisTemplate.opsForValue().get(key);
//    }
//
//    public void delete(String key) {
////        redisTemplate.delete(key);
//    }
//}
