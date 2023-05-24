package com.demo.security.service;

import com.demo.common.entity.User;

public interface LoginService {

    User adminRegistration();

    String login(String username, String password);
}
