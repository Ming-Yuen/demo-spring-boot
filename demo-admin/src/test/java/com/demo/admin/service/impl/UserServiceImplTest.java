//package com.demo.admin.service.impl;
//
//import com.demo.admin.dto.UserRegisterRequest;
//import com.demo.admin.entity.UserInfo;
//import com.demo.admin.enums.Gender;
//import com.demo.common.entity.enums.UserRole;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//class UserServiceImplTest {
//
//    @BeforeEach
//    void setUp() {
//        UserRegisterRequest userRegisterRequest = UserRegisterRequest.builder()
//                .userName("ChanTaiMan").password("12345678").firstName("Tai Man").lastName("Chan")
//                .gender(Gender.male).email("demo@gmail.com").phone("12345678").role(UserRole.user).build();
//
//    }
//
//    @Test
//    void register() {
//    }
//
//    @Test
//    void saveUserEncryptPassword() {
//    }
//
//    @Test
//    void saveUser() {
//    }
//
//    @Test
//    void findByUserName() {
//    }
//
//    @Test
//    void getManageRoles() {
//    }
//
//    @Test
//    void login() {
//    }
//
//    @Test
//    void query() {
//    }
//}