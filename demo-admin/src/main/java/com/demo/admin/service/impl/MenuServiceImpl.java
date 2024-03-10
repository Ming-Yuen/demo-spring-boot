package com.demo.admin.service.impl;

import com.demo.admin.MenuMapper;
import com.demo.admin.dao.MenuDao;
import com.demo.admin.dto.MenuQueryRequest;
import com.demo.admin.dto.MenuUpdateRequest;
import com.demo.admin.entity.MenuStructure;
import com.demo.admin.entity.QMenuStructure;
import com.demo.admin.vo.MenuStructureResponse;
import com.demo.common.entity.enums.UserRole;
import com.demo.admin.service.MenuService;
import com.demo.admin.service.UserService;
import com.querydsl.jpa.impl.JPADeleteClause;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.*;

@Slf4j
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private UserService userService;
    @Autowired
    private MenuMapper menuMapper;
    @Autowired
    private EntityManager entityManager;

    @Override
    public MenuStructureResponse getStructure(MenuQueryRequest menuQueryRequest){
        if(menuQueryRequest == null){
            return convertToResponse(menuDao.findByRoleIdIn(Collections.singletonList(0L)));
        }
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        UserRole role_level = userService.findByUserName(username).getRole();

        Map<Long, MenuStructureResponse.MenuTree> menuTreeMap = new HashMap<>();

        Collection<Long> manageRoleId = userService.getManageRoles(role_level);
//        menuDao.findByRoleIdIn(manageRoleId)
//                .forEach(x->{
//                    MenuStructureResponse.MenuTree menuTree = null;
//                    if((menuTree = menuTreeMap.get(x.getParent())) == null){
//                        menuTree = new MenuStructureResponse.MenuTree(x);
//                        menuTreeResponse.getMenu().add(menuTree);
//                    }else {
//                        menuTree.getChild().add(new MenuStructureResponse.MenuTree(x));
//                    }
//                    menuTreeMap.put(x.getId(), menuTree);
//                });
//        List<MenuStructureResponse.MenuTree> menu = menuTreeResponse.getMenu();
//        for (int i = 0; i < menu.size(); i++) {
//            if(menu.get(i).getChild().isEmpty()){
//                menu.remove(i);
//            }
//        }
        return convertToResponse(menuDao.findByRoleIdIn(manageRoleId));
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
            menuTreeMap.put(menuItem.getTitle(), menuTree);
        });
        return menuTreeResponse;
    }

    @Transactional
    @Override
    public void menuUpdate(MenuUpdateRequest menuUpdateRequest) {
        QMenuStructure qMenuStructure = QMenuStructure.menuStructure;
        long delete_count = new JPADeleteClause(entityManager, qMenuStructure).where(qMenuStructure.type.eq("web")).execute();
        log.debug("menu delete count : {}", delete_count);

        menuDao.peristAllAndFlush(menuMapper.convert(menuUpdateRequest.getMenu()));
    }
}
