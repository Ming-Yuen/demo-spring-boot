package com.demo.admin.service;

import com.demo.security.entity.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public interface UserService {

    String login(@NotBlank String userName, @NotBlank String password);

    User adminRegistration();

    void update(User user);
}
