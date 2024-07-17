package com.demo.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer status = 1;
    private String message;
    private T data;
    public ApiResponse setError(String message) {
        this.status = -1;
        this.message = message;
        this.data = null;
        return this;
    }
    public ApiResponse setInternalError() {
        this.status = -1;
        this.message = "Internal Error";
        this.data = null;
        return this;
    }

    public ApiResponse isSuccess(T data) {
        this.status = 1;
        this.data = data;
        return this;
    }
}
