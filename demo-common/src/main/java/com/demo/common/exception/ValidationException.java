package com.demo.common.exception;

public class ValidationException extends Exception{
    public ValidationException(String message){
        super(message);
    }
    public ValidationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
