package com.demo.security.controller;

import com.demo.common.controller.ControllerPath;
import com.demo.security.bo.AdminLoginRequest;
import com.demo.security.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping(ControllerPath.login)
public class LoginController {

    @Autowired
    private LoginService login;


    @PostMapping(path = ControllerPath.jwt, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> login(@Valid @RequestBody AdminLoginRequest admin){
        Map<String, String> token = new HashMap<>();
        token.put("token", login.login(admin.getUserName(), admin.getPassword()));
        return token;
    }
}
