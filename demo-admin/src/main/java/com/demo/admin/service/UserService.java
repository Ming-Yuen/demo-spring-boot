package com.demo.admin.service;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User adminRegistration();

    List<User> update(List<UserRegisterRequest> user);

    User findByUsername(String userId);
}
