package com.demo.admin.controller;

import com.demo.admin.dto.*;
import com.demo.admin.service.UserService;
import com.demo.admin.vo.UserQueryResponse;
import com.demo.admin.vo.UserRegisterResponse;
import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.ApiResponse;
import com.demo.common.util.ValidList;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerPath.USER)
@AllArgsConstructor
public class UserController {
    private UserService userService;
    @PostMapping(path = ControllerPath.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserRegisterResponse>> update(@RequestBody @Validated  ValidList<UserRegisterRequest> request){
        userService.updateUserRequest(request);
        List<String> userNameList = request.stream().map(UserRegisterRequest::getUserName).collect(Collectors.toList());
        return new ResponseEntity<>(new ApiResponse<>().isSuccess(userNameList), HttpStatus.OK);
    }
    @PostMapping(path = ControllerPath.TOKEN, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> tokenEnquiry(@Valid @RequestBody TokenRequest request){
        String token = userService.login(request.getUsername(), request.getPassword());
        return new ResponseEntity<>(new ApiResponse<>().isSuccess(token), HttpStatus.OK);
    }
    @PostMapping(path = ControllerPath.QUERY, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<UserQueryResponse>> query(@Valid @RequestBody UserQueryRequest request){
        return new ResponseEntity<>(new ApiResponse<>().isSuccess(userService.userQueryRequest(request)), HttpStatus.OK);
    }
}
