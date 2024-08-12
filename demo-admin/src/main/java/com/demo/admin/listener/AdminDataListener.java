package com.demo.admin.listener;

import com.demo.admin.constant.UserConstant;
import com.demo.admin.entity.Users;
import com.demo.admin.security.AdminUserDetails;
import com.demo.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Component
public class AdminDataListener implements CommandLineRunner {
    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {
        boolean existing = userService.existsByFirstUsername("admin");
        if(!existing){
            Users users = new Users();
            users.setId(1L);
            users.setUserName("admin");
            users.setFirstName("temp");
            users.setLastName("temp");
            users.setUserPassword("admin");
            users.setPrivilege(UserConstant.PRIVILEGE_ADMIN);
            users.setGender("none");

            AdminUserDetails userDetails = new AdminUserDetails(users);;
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);

            userService.updateUser(Arrays.asList(users));
        }
    }
}