package com.demo.admin.service;

import com.demo.admin.eneity.UserEntity;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public interface AdminService {

    String login(@NotBlank String userName, @NotBlank String password);

    UserEntity getUser(@NotBlank String userName, @NotBlank String password);

}
