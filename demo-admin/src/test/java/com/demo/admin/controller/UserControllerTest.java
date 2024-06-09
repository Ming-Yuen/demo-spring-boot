package com.demo.admin.controller;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.enums.Gender;
import com.demo.admin.service.UserService;
import com.demo.common.entity.enums.UserRole;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;

    @Test
    void update() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserName("ChanTaiMan");
        userRegisterRequest.setPassword("12345678");
        userRegisterRequest.setFirstName("Tai Man");
        userRegisterRequest.setLastName("Chan");
        userRegisterRequest.setGender(Gender.male);
        userRegisterRequest.setEmail("demo@gmail.com");
        userRegisterRequest.setPhone("12345678");
        userRegisterRequest.setRole(UserRole.user);

//        Mockito.when(userService.findByUserName())
        Mockito.doNothing().when(userService).register(Arrays.asList(userRegisterRequest));
    }

    @Test
    void tokenEnquiry() {
    }

    @Test
    void query() {
    }
}