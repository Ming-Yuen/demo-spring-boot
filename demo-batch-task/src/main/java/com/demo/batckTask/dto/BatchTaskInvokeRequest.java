package com.demo.batckTask.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BatchTaskInvokeRequest {
    @NotBlank
    private String taskName;
}
