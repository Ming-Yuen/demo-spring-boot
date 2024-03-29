package com.demo.common.controller;

import com.demo.common.dto.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        defaultResponse.setSuccess(false);
        defaultResponse.setErrorMessage(Arrays.asList(t.getMessage()));
        defaultResponse.setErrorNum(1);
        return defaultResponse;
    }

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object throwable(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        defaultResponse.setSuccess(false);

        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        List<String> message = new ArrayList<String>();
        errors.forEach(error->
                message.add(error.getField() + ":" + error.getDefaultMessage())
        );
        defaultResponse.setErrorMessage(message);
        return defaultResponse;
    }
}
