package com.demo.admin.service.impl;

import com.demo.admin.entity.UserInfo;
import com.demo.admin.mapper.MenuMapper;
import com.demo.admin.repository.MenuRepository;
import com.demo.admin.dto.MenuQueryRequest;
import com.demo.admin.dto.MenuUpdateRequest;
import com.demo.admin.entity.MenuStructure;
import com.demo.admin.vo.MenuStructureResponse;
import com.demo.admin.enums.PrivilegeType;
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
    private MenuRepository menuRepository;
    private UserService userService;
    private MenuMapper menuMapper;

    @Override
    public MenuStructureResponse getStructure(MenuQueryRequest menuQueryRequest) throws ValidationException {
        if(menuQueryRequest == null){
            return convertToResponse(menuRepository.findByPrivilegeIn(PrivilegeType.user));
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        if(userService.findByUserName(UserInfo.SelectUserName.class, username).isEmpty()){
            throw new ValidationException("user is not registered");
        }
        PrivilegeType[] privilegeTypes = userService.findByUserName(UserInfo.SelectUserRole.class, username).stream().map(x->x.privilegeType()).toArray(PrivilegeType[]::new);

        Map<Long, MenuStructureResponse.MenuTree> menuTreeMap = new HashMap<>();

        List<PrivilegeType> privilegeTypeList = userService.getSubPrivilege(privilegeTypes);
        return convertToResponse(menuRepository.findByPrivilegeIn(privilegeTypeList.toArray(new PrivilegeType[]{})));
    }

    private MenuStructureResponse convertToResponse(List<MenuStructure> menuStructures){
        MenuStructureResponse menuTreeResponse = new MenuStructureResponse();
        Map<String, MenuStructureResponse.MenuTree> menuTreeMap = new HashMap<>();
        menuStructures.forEach(menuItem->{
            MenuStructureResponse.MenuTree menuTree = null;
            if(menuItem.getParent() == null){
                menuTreeResponse.getMenu().add(menuTree = menuMapper.convert(menuItem));
            }else if((menuTree = menuTreeMap.get(menuItem.getParent())) != null){
                if(menuTree.getChild() ==null){
                    menuTree.setChild(new ArrayList<>());
                }
                menuTree.getChild().add(menuMapper.convert(menuItem));
            }
            menuTreeMap.put(menuItem.getName(), menuTree);
        });
        return menuTreeResponse;
    }

    @Transactional
    @Override
    public void menuUpdate(MenuUpdateRequest menuUpdateRequest) {
        long delete_count = menuRepository.deleteByType("web");
        log.debug("menu delete count : {}", delete_count);

        menuRepository.saveAll(menuMapper.convert(menuUpdateRequest.getMenu()));
    }
}
