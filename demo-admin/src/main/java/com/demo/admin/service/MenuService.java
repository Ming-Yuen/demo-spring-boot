package com.demo.admin.service;

import com.demo.admin.dto.MenuQueryRequest;
import com.demo.admin.dto.MenuUpdateRequest;
import com.demo.admin.vo.MenuStructureResponse;
import com.demo.common.dto.DefaultResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MenuService{

    @Transactional
    void menuUpdate(MenuUpdateRequest menuUpdateRequest);

    MenuStructureResponse getStructure(MenuQueryRequest menuQueryRequest);
}
