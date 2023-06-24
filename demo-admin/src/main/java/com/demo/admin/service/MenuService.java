package com.demo.admin.service;

import com.demo.admin.dto.MenuStructureResponse;
import org.springframework.transaction.annotation.Transactional;


public interface MenuService{
    MenuStructureResponse getStructure();
}
