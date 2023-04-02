package com.demo.admin.service;

import com.demo.security.entity.Admin;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public interface AdminService {

    String login(@NotBlank String userName, @NotBlank String password);

    Admin getUser(@NotBlank String userName, @NotBlank String password);

    Admin adminRegistration();
}
