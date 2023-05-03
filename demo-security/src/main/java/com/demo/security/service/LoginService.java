package com.demo.security.service;

import com.demo.security.token.JwtManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface LoginService {

    String login(String username, String password);
}
