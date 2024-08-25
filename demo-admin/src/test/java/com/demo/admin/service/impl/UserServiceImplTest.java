//package com.demo.admin.service.impl;
//
//import com.demo.common.dto.UserRegisterRequest;
//import com.demo.admin.entity.UserInfo;
//import com.demo.admin.enums.Gender;
//import com.demo.admin.enums.PrivilegeType;
//import com.demo.admin.repository.UserRepository;
//import com.demo.admin.service.UserService;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.Arrays;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.ArgumentMatchers.any;
//
//@ExtendWith(MockitoExtension.class)
//class UserServiceImplTest {
//    @Mock
//    private UserRepository userRepository;
//    @InjectMocks
//    private UserService userService;
//
//    @Test
//    void updateUserRequest() {
//        Mockito.when(userRepository.findByUserNameIn(any(), any())).thenReturn(Arrays.asList(new UserInfo()));
//
//        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
//        userRegisterRequest.setUserName("ChanTaiMan");
//        userRegisterRequest.setUserPassword("12345678");
//        userRegisterRequest.setFirstName("Tai Man");
//        userRegisterRequest.setLastName("Chan");
//        userRegisterRequest.setGender(Gender.male);
//        userRegisterRequest.setEmail("demo@gmail.com");
//        userRegisterRequest.setPhone("12345678");
//        userRegisterRequest.setPrivilege(PrivilegeType.user);
//
//        userService.updateUserRequest(Arrays.asList(userRegisterRequest));
//    }
//
//    @Test
//    void updateUserMaster() {
//    }
//
//    @Test
//    void findByUserName() {
//    }
//
//    @Test
//    void getSubPrivilege() {
//    }
//
//    @Test
//    void login() {
//    }
//
//    @Test
//    void userQueryRequest() {
//    }
//}