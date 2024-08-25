package com.demo.admin.service;

import com.demo.common.dto.MenuQueryRequest;
import com.demo.common.dto.MenuUpdateRequest;
import com.demo.common.vo.MenuStructureResponse;
import com.demo.common.exception.ValidationException;
import org.springframework.transaction.annotation.Transactional;

public interface MenuService{

    @Transactional
    void menuUpdate(MenuUpdateRequest menuUpdateRequest);

    MenuStructureResponse getStructure(MenuQueryRequest menuQueryRequest) throws ValidationException;
}
