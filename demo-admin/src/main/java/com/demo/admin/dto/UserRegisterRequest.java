package com.demo.admin.dto;

import com.demo.admin.enums.Gender;
import com.demo.common.entity.enums.UserRole;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class UserRegisterRequest {
    @NotBlank
    private String userName;
    private String firstName;
    private String lastName;
    @NotBlank
    private String password;
    @NotBlank
    private Gender gender;
    private String email;
    private String phone;
    @NotBlank
    private UserRole roleId;
}
