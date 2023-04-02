package com.demo.admin.controller;

import com.demo.admin.bo.AdminLoginRequest;
import com.demo.admin.service.AdminService;
import com.demo.common.controller.ControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(ControllerPath.admin)
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(path = ControllerPath.jwtLogin, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> jwtLogin(@Valid @RequestBody AdminLoginRequest admin){
        Map<String, String> token = new HashMap<>();
        token.put("token", adminService.login(admin.getUserName(), admin.getPassword()));
        return token;
    }

    @PostMapping(path = "123", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, String> jwtLogin2(@Valid @RequestBody AdminLoginRequest admin){
//        Map<String, String> token = new HashMap<>();
//        token.put("token", adminService.login(admin.getUserName(), admin.getPassword()));
        return null;
    }
}
