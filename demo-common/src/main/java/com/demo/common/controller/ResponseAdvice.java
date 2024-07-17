package com.demo.common.controller;

import com.demo.common.dto.ApiResponse;
import com.demo.common.exception.ValidationException;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ResponseAdvice implements ResponseBodyAdvice<Object> {
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
    @ExceptionHandler(ValidationException.class)
    public Object validationException(ValidationException exception) {
        log.error(exception.getMessage(), exception);
        return new ApiResponse().setError(exception.toString());
    }

    @ResponseBody
    @ExceptionHandler(HandlerMethodValidationException.class)
    public Object handlerMethodValidationException(Throwable t) {
        log.error(t.getMessage(), t);
        return new ApiResponse().setError(t.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Object httpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e);
        return new ApiResponse().setError("Http message not readable");
    }

    @ResponseBody
    @ExceptionHandler(ExpiredJwtException.class)
    public Object expiredJwtException(ExpiredJwtException t) {
        log.error(t.getMessage(), t);
        return new ApiResponse().setError(t.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(IllegalArgumentException.class)
    public Object illegalArgumentException(IllegalArgumentException t) {
        log.error(t.getMessage(), t);
        return new ApiResponse().setError(t.getMessage());
    }
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);

        List<FieldError> errors = e.getBindingResult().getFieldErrors();
        List<String> message = new ArrayList<String>();
        errors.forEach(error->
                message.add(error.getField() + ":" + error.getDefaultMessage())
        );
        return new ApiResponse().setError(message.toString());
    }
}
