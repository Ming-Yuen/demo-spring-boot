package com.demo.admin.controller;

import com.demo.admin.bo.AdminLoginRequest;
import com.demo.admin.service.UserService;
import com.demo.common.controller.ControllerPath;
import com.demo.security.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(ControllerPath.user)
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = ControllerPath.jwtLogin, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> login(@Valid @RequestBody AdminLoginRequest admin){
        Map<String, String> token = new HashMap<>();
        token.put("token", userService.login(admin.getUserName(), admin.getPassword()));
        return token;
    }
    @PostMapping(path = ControllerPath.update, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody User user){
        userService.update(user);
    }
}
