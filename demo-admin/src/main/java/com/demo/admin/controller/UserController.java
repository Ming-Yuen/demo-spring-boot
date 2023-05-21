package com.demo.admin.controller;

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
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerPath.user)
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping(path = ControllerPath.update, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DefaultResponse register(@Valid @RequestBody List<UserRegisterRequest> user){
        return new UserRegisterResponse(userService.update(user).stream().map(x->x.getUsername()).collect(Collectors.toList()));
    }
    @PostMapping(path = ControllerPath.adjustment, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void adjustment(@Valid @RequestBody User user){

    }
}
