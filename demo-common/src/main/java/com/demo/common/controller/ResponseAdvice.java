package com.demo.common.controller;

import com.demo.common.controller.bo.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@RestControllerAdvice
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice<Object> {

    @Autowired
    private DefaultResponse defaultResponse;
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return false;
    }

    @Override
    @SneakyThrows
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        return body;
    }

    @ResponseBody
    @ExceptionHandler(Throwable.class)
    public Object throwable(Throwable t) {
        log.error(t.getMessage(), t);
        defaultResponse.setError(true);
        defaultResponse.setErrorMessage(t.getMessage());
        defaultResponse.setErrorCode("100");
        return defaultResponse;
    }
}
