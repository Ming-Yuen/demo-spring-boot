package com.demo.admin.controller;

import com.demo.admin.vo.MenuStructureResponse;
import com.demo.admin.service.MenuService;
import com.demo.common.controller.ControllerPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ControllerPath.menu)
public class MenuController {

    @Autowired
    private MenuService menuService;
    @PostMapping(path = ControllerPath.structure + ControllerPath.query, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MenuStructureResponse enquiry(){
        return menuService.getStructure();
    }
}
