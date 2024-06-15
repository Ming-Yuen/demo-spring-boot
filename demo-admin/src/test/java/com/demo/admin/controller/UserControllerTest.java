package com.demo.admin.controller;

import com.demo.admin.dto.UserRegisterRequest;
import com.demo.admin.enums.Gender;
import com.demo.admin.service.UserService;
import com.demo.common.controller.ControllerPath;
import com.demo.admin.enums.PrivilegeType;
import com.demo.common.util.Json;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private UserService userService;
    @Test
    void update() throws Exception {
        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
        userRegisterRequest.setUserName("ChanTaiMan");
        userRegisterRequest.setUserPassword("12345678");
        userRegisterRequest.setFirstName("Tai Man");
        userRegisterRequest.setLastName("Chan");
        userRegisterRequest.setGender(Gender.male);
        userRegisterRequest.setEmail("demo@gmail.com");
        userRegisterRequest.setPhone("12345678");
        userRegisterRequest.setPrivilege(PrivilegeType.user);

        mockMvc.perform(post(ControllerPath.USER +ControllerPath.UPDATE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Json.objectMapper.writeValueAsString(Arrays.asList(userRegisterRequest)))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(1));

    }
    @BeforeEach
    public void setUp() {
    }

    @Test
    void tokenEnquiry() {
//        UserRegisterRequest userRegisterRequest = new UserRegisterRequest();
//        userRegisterRequest.setUserName("ChanTaiMan");
//        userRegisterRequest.setPassword("12345678");
//        userRegisterRequest.setFirstName("Tai Man");
//        userRegisterRequest.setLastName("Chan");
//        userRegisterRequest.setGender(Gender.male);
//        userRegisterRequest.setEmail("demo@email.com");
//        userRegisterRequest.setPhone("12345678");
//        userRegisterRequest.setRole(UserRole.user);
    }

    @Test
    void query() {
    }
}