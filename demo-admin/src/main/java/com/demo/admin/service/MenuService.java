package com.demo.admin.service;

import com.demo.admin.dto.MenuQueryRequest;
import com.demo.admin.dto.MenuUpdateRequest;
import com.demo.admin.vo.MenuStructureResponse;
import com.demo.common.exception.ValidationException;
import org.springframework.transaction.annotation.Transactional;

public interface MenuService{

    @Transactional
    void menuUpdate(MenuUpdateRequest menuUpdateRequest);

    MenuStructureResponse getStructure(MenuQueryRequest menuQueryRequest) throws ValidationException;
}
