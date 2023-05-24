package com.demo.admin.controller;

import com.demo.admin.dto.MenuStructureResponse;
import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.dto.UserRegisterResponse;
import com.demo.admin.service.MenuService;
import com.demo.common.controller.ControllerPath;
import com.demo.common.controller.dto.DefaultResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(ControllerPath.menu)
public class MenuController {

    @Autowired
    private MenuService menuService;
    @PostMapping(path = ControllerPath.structure + ControllerPath.enquiry)//, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MenuStructureResponse enquiry(){
        return menuService.getStructure();
    }
}
