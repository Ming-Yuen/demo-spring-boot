package com.demo.admin.controller;

import com.demo.admin.dto.*;
import com.demo.admin.service.UserService;
import com.demo.admin.vo.TokenResponse;
import com.demo.admin.vo.UserQueryResponse;
import com.demo.admin.vo.UserRegisterResponse;
import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerPath.user)
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping(path = ControllerPath.update, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DefaultResponse update(@Valid @RequestBody List<UserRegisterRequest> request){
        userService.register(request);
        return new UserRegisterResponse(request.stream().map(UserRegisterRequest::getUserName).collect(Collectors.toList()));
    }
    @PostMapping(path = ControllerPath.token, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public TokenResponse login(@Valid @RequestBody TokenRequest request){
        return new TokenResponse(userService.login(request.getUsername(), request.getPassword()));
    }
    @PostMapping(path = ControllerPath.query, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserQueryResponse query(@Valid @RequestBody UserQueryRequest request){
        return new UserQueryResponse(userService.query(request));
    }
}
