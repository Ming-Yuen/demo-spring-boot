package com.demo.schedule.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BatchTaskInvokeRequest {
    @NotBlank
    private String name;
}
