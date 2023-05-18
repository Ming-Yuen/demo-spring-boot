package com.demo.admin.dto;

import com.demo.common.controller.dto.BaseDto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
@Data
public class UserRegisterRequest extends BaseDto {
    @NotBlank
    private String username;
    private String firstName;
    private String lastName;
    @NotBlank
    private String password;
    @NotBlank
    private String gender;
    private String email;
    private String phone;
    @NotBlank
    private String role;
}
