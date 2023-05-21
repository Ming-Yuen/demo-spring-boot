package com.demo.admin.service.impl;

import com.demo.admin.dao.MenuDao;
import com.demo.admin.service.MenuService;
import com.demo.admin.service.UserService;
import com.demo.security.util.UserContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;
    @Autowired
    private UserService userService;

    public void getStructure(){
        String role = UserContextHolder.getUser().getUserRole();
        menuDao.findByRoleIn(userService.getUserRoles(null));
    }
}
