package com.demo.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public <T> void set(Function<? super T, ? extends String> key, List<T> value) {
        value.forEach(x->{
            redisTemplate.opsForValue().set(key.apply(x), x);
        });
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
}
