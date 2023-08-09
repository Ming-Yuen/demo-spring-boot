package com.demo.common.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@Scope("prototype")
public class DefaultResponse {
    private boolean success = true;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer errorNum;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<String> errorMessage;
}
