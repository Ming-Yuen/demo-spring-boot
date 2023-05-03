package com.demo.admin.service;

import com.demo.admin.entity.User;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotBlank;

@Service
public interface UserService {

    User adminRegistration();

    void register(User user);

    void update(User user);

    User findByUsername(String userId);
}
