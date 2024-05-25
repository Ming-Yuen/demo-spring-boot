package com.demo.admin.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;
@Data
public class MenuQueryRequest {
    @NotBlank
    private String menuType;
}
