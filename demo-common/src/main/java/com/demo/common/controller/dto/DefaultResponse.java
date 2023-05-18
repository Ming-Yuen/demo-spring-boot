package com.demo.common.controller.dto;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class DefaultResponse {

    private boolean isError;
    private Integer errorCode = 0;
    private String errorMessage;
}
