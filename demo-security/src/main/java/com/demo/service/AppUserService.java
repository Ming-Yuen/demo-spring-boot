package com.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class AppUserService {
    private AppUserRepository repository;
    private BCryptPasswordEncoder passwordEncoder;

    public AppUserService(AppUserRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public AppUserResponse createUser(AppUserRequest request) {
        // 其餘略過

        AppUser user = AppUserConverter.toAppUser(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = repository.insert(user);

        return AppUserConverter.toAppUserResponse(user);
    }
    // 其餘略過
}