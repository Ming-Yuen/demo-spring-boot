package com.demo.admin.controller;

import com.demo.admin.dto.MenuQueryRequest;
import com.demo.admin.dto.MenuUpdateRequest;
import com.demo.admin.vo.MenuStructureResponse;
import com.demo.admin.service.MenuService;
import com.demo.common.controller.ControllerPath;
import com.demo.common.dto.DefaultResponse;
import com.demo.common.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping(ControllerPath.STRUCTURE)
public class StructureController {

    @Autowired
    private MenuService menuService;

    @GetMapping(path = ControllerPath.MENU+ControllerPath.DEFAULT+ControllerPath.QUERY)
    public MenuStructureResponse menuEnquiry() throws ValidationException {
        return menuService.getStructure(null);
    }

    @PostMapping(path = ControllerPath.MENU+ControllerPath.QUERY, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MenuStructureResponse menuEnquiry(@Valid @RequestBody MenuQueryRequest menuQueryRequest) throws ValidationException {
        return menuService.getStructure(menuQueryRequest);
    }

    @PostMapping(path = ControllerPath.MENU+ControllerPath.UPDATE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public DefaultResponse menuUpdate(@Valid @RequestBody MenuUpdateRequest menuUpdateRequest){
        menuService.menuUpdate(menuUpdateRequest);
        return new DefaultResponse();
    }
}
