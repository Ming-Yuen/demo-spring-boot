package com.demo.admin.service.impl;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.enums.Gender;
import com.demo.admin.enums.PrivilegeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @BeforeEach
    void setUp() {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserName("ChanTaiMan");
        userRegisterRequest.setUserPassword("12345678");
        userRegisterRequest.setFirstName("Tai Man");
        userRegisterRequest.setLastName("Chan");
        userRegisterRequest.setGender(Gender.male);
        userRegisterRequest.setEmail("demo@gmail.com");
        userRegisterRequest.setPhone("12345678");
        userRegisterRequest.setPrivilege(PrivilegeType.user);

    }

    @Test
    void register() {
    }

    @Test
    void saveUserEncryptPassword() {
    }

    @Test
    void saveUser() {
    }

    @Test
    void findByUserName() {
    }

    @Test
    void getManageRoles() {
    }

    @Test
    void login() {
    }

    @Test
    void query() {
    }
}