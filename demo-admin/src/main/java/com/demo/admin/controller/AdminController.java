package com.demo.admin.controller;

import com.demo.admin.service.AdminService;
import com.demo.controller.ControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(ControllerPath.admin)
public class AdminController {

    @Autowired
    private AdminService adminService;

    @PostMapping(path = ControllerPath.jwtLogin)
    public Map<String, String> jwtLogin(@RequestParam String username, @RequestParam String password){
        Map<String, String> token = new HashMap<>();
        token.put("token", adminService.login(username, password));
        return token;
    }
}
