package com.demo.common.controller.bo;

import lombok.Data;
@Data
public class DefaultResponse {

    private boolean isError;
    private String errorCode;
    private String errorMessage;
}
