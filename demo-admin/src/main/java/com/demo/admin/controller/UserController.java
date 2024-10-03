package com.demo.admin.controller;

import com.demo.common.dto.TokenRequest;
import com.demo.common.dto.UserRegisterRequest;
import com.demo.admin.service.UserService;
import com.demo.common.vo.UserRegisterResponse;
import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.ApiResponse;
import com.demo.common.util.ValidList;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerPath.USER)
//@AllArgsConstructor
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping(path = ControllerPath.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public UserRegisterResponse update(@RequestBody @Validated  ValidList<UserRegisterRequest> request){
        userService.updateUserRequest(request);
        List<String> userNameList = request.stream().map(UserRegisterRequest::getUserName).collect(Collectors.toList());
        return new UserRegisterResponse(userNameList);
    }
    @Value ("${demo}")
    private String config;
    @PostMapping(path = ControllerPath.TOKEN, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ApiResponse tokenEnquiry(@Valid @RequestBody TokenRequest request){
        String token = userService.login(request.getUsername(), request.getPassword());
        System.out.println("----------"+config);
        return new ApiResponse<>().isSuccess(token);
    }
}
