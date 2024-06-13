package com.demo.admin.dto;

import com.demo.admin.enums.Gender;
import com.demo.common.entity.enums.UserRole;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.NotBlank;
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
    private Gender gender;
    private String email;
    private String phone;
    @NotNull
    private UserRole role;
}
