package com.demo.admin.controller;

import com.demo.admin.dto.AdminLoginRequest;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.dto.UserRegisterResponse;
import com.demo.admin.service.UserService;
import com.demo.common.controller.ControllerPath;
import com.demo.common.controller.dto.DefaultResponse;
import com.demo.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerPath.admin)
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping(path = ControllerPath.update, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DefaultResponse update(@Valid @RequestBody List<UserRegisterRequest> user){
        userService.update(user);
        return new UserRegisterResponse(user.stream().map(x->x.getUsername()).collect(Collectors.toList()));
    }
    @PostMapping(path = ControllerPath.token, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> login(@Valid @RequestBody AdminLoginRequest admin){
        Map<String, String> token = new HashMap<>();
        token.put("token", userService.login(admin.getUsername(), admin.getPassword()));
        return token;
    }
}
