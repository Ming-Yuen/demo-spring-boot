package com.demo.admin.controller;

import com.demo.admin.entity.User;
import com.demo.admin.service.UserService;
import com.demo.common.controller.ControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(ControllerPath.user)
public class UserController {

    @Autowired
    private UserService userService;
    @PostMapping(path = ControllerPath.register, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void register(@Valid @RequestBody User user){
        userService.update(user);
    }

    @PostMapping(path = ControllerPath.update, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void update(@Valid @RequestBody User user){
        userService.update(user);
    }
    @PostMapping(path = ControllerPath.adjustment, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void adjustment(@Valid @RequestBody User user){

    }
}
