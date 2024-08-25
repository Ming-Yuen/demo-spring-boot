package com.demo.common.dto;

import lombok.Data;

import jakarta.validation.constraints.NotBlank;

@Data
public class TokenRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
