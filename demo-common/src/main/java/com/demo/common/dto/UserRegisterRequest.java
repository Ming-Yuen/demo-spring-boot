package com.demo.common.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserRegisterRequest {
    @NotBlank
    private String userName;
    private String firstName;
    private String lastName;
    @NotBlank
    private String userPassword;
    @NotNull
    private String gender;
    private String email;
    private String phone;
    @NotNull
    private Integer privilege;
}
