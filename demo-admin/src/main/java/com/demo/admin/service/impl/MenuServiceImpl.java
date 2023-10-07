package com.demo.admin.service.impl;

import com.demo.admin.dao.MenuDao;
import com.demo.admin.dto.MenuStructureResponse;
import com.demo.admin.entity.enums.RoleLevelEnum;
import com.demo.admin.service.MenuService;
import com.demo.admin.service.UserService;
import com.demo.admin.util.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private UserService userService;

    public MenuStructureResponse getStructure(){
        RoleLevelEnum role_level = UserContextHolder.getUser().getRoleLevel();

        Map<Long, MenuStructureResponse.MenuTree> menuTreeMap = new HashMap<>();
        MenuStructureResponse menuTreeResponse = new MenuStructureResponse();

        Collection<Long> manageRoleId = userService.getManageRoles(role_level);
        menuDao.findByRoleIdIn(manageRoleId)
                .forEach(x->{
                    MenuStructureResponse.MenuTree menuTree = null;
                    if((menuTree = menuTreeMap.get(x.getParent())) == null){
                        menuTree = new MenuStructureResponse.MenuTree(x);
                        menuTreeResponse.getMenu().add(menuTree);
                    }else {
                        menuTree.getChild().add(new MenuStructureResponse.MenuTree(x));
                    }
                    menuTreeMap.put(x.getId(), menuTree);
                });
        List<MenuStructureResponse.MenuTree> menu = menuTreeResponse.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            if(menu.get(i).getChild().isEmpty()){
                menu.remove(i);
            }
        }
        return menuTreeResponse;
    }
}
