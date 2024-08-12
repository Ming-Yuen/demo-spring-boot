package com.demo.admin.service.impl;

import com.demo.admin.mapping.MenuMapping;
import com.demo.admin.dao.MenuMapper;
import com.demo.admin.dto.MenuQueryRequest;
import com.demo.admin.dto.MenuUpdateRequest;
import com.demo.admin.entity.MenuStructure;
import com.demo.admin.vo.MenuStructureResponse;
import com.demo.admin.service.MenuService;
import com.demo.admin.service.UserService;
import com.demo.common.exception.ValidationException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class MenuServiceImpl implements MenuService {
    private MenuMapper menuMapper;
    private UserService userService;
    private MenuMapping menuMapping;

    @Override
    public MenuStructureResponse getStructure(MenuQueryRequest menuQueryRequest) throws ValidationException {
        if(menuQueryRequest == null){
            return convertToResponse(menuMapper.findByPrivilege(500));
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userService.findByFirstUserName(username) == null){
            throw new ValidationException("user is not registered");
        }
        Integer privilegeType = userService.findByPrivilegeType(username);
        return convertToResponse(menuMapper.findByPrivilege(privilegeType));
    }

    private MenuStructureResponse convertToResponse(List<MenuStructure> menuStructures){
        MenuStructureResponse menuTreeResponse = new MenuStructureResponse();
        Map<String, MenuStructureResponse.MenuTree> menuTreeMap = new HashMap<>();
        menuStructures.forEach(menuItem->{
            MenuStructureResponse.MenuTree menuTree = null;
            if(menuItem.getParent() == null){
                menuTreeResponse.getMenu().add(menuTree = menuMapping.convert(menuItem));
            }else if((menuTree = menuTreeMap.get(menuItem.getParent())) != null){
                if(menuTree.getChild() ==null){
                    menuTree.setChild(new ArrayList<>());
                }
                menuTree.getChild().add(menuMapping.convert(menuItem));
            }
            menuTreeMap.put(menuItem.getName(), menuTree);
        });
        return menuTreeResponse;
    }

    @Transactional
    @Override
    public void menuUpdate(MenuUpdateRequest menuUpdateRequest) {
        long delete_count = menuMapper.deleteByType("web");
        log.debug("menu delete count : {}", delete_count);

        menuMapper.insert(menuMapping.convert(menuUpdateRequest.getMenu()));
    }
}
