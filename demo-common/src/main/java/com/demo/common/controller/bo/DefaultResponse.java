package com.demo.common.controller.bo;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Data
@Component
@Scope("prototype")
public class DefaultResponse {

    private boolean isError;
    private String errorCode;
    private String errorMessage;
}
