package com.demo.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Json {
    public static final ObjectMapper objectMapper = new ObjectMapper();
    static {
        objectMapper.registerModule(new JavaTimeModule());
    }
}
