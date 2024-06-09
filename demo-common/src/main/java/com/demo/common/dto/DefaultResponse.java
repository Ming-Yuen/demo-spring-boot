package com.demo.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
public class DefaultResponse {
    private boolean success = true;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorNum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errorMessage;
}
