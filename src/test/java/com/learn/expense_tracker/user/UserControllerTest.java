package com.learn.expense_tracker.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.expense_tracker.common.dto.SuccessCode;
import com.learn.expense_tracker.config.TestConfig;
//import com.learn.expense_tracker.config.TestSecurityConfig;
import com.learn.expense_tracker.security.CustomUserDetailService;
import com.learn.expense_tracker.security.JwtService;
import com.learn.expense_tracker.user.request.UpdateRequest;
import com.learn.expense_tracker.user.request.UserRequest;
import com.learn.expense_tracker.user.response.UserResponse;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.context.annotation.Import;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
//@WebMvcTest(UserController.class)
@SpringBootTest
@AutoConfigureMockMvc
@Import( TestConfig.class)
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private CustomUserDetailService customUserDetailService;

    @Test
    @WithMockUser(username = "test1", roles = "ADMIN")
    void getAll_shouldReturnList() throws Exception {
        List<UserResponse> mockList = List.of(
                UserResponse.builder()
                        .id(1L)
                        .username("test1")
                        .email("test@gmail.com")
                        .avatar("avatar")
                        .role("ROLE_USER")
                        .build(),
                UserResponse.builder()
                        .id(2L)
                        .username("test2")
                        .email("test2@gmail.com")
                        .avatar("avatar2")
                        .role("ROLE_ADMIN")
                        .build()
        );

        when(userService.findAll()).thenReturn(mockList);

        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(jsonPath("$['data'][0].username").value("test1"))
                .andExpect(jsonPath("$['data'][1].username").value("test2"));
    }

    @Test
    @WithMockUser(username = "test1", roles = "ADMIN")
    void createUser_shouldReturnCreatedUser() throws Exception {
        UserRequest request = new UserRequest("test12", "123", "test@gmail.com", "avatar", "ROLE_USER");

        when(userService.save(any(UserRequest.class))).thenReturn(SuccessCode.USER_CREATED);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").value("USER_CREATED"));
    }

//    @Test
//    @WithMockUser(username = "test1", roles = "USER")
//    void updateUser_shouldReturnSuccessCode() throws Exception {
//        Long userId = 1L;
//        UpdateRequest updateRequest = new UpdateRequest("update_test", "test456@gmail.com", "update_avatar", "ROLE_USER");
//
//        User mockUser = new User();
//        mockUser.setId(userId);
//        mockUser.setUsername("test1");
//        mockUser.setEmail("test123@gmail.com");
//        mockUser.setPassword("password");
//        mockUser.setAvatar("avatar");
//        mockUser.setRole(Set.of("ROLE_USER"));
//
//        String token = "mocked-jwt-token";
//        when(jwtService.generateToken(any(User.class))).thenReturn(token);
//
//
//        when(userService.update(any(UpdateRequest.class), eq(mockUser.getId())))
//                .thenReturn(SuccessCode.USER_UPDATED);
//
//        mockMvc.perform(put("/api/users/me")
//                        .header("Authorization", "Bearer " + token)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(updateRequest)))
//                        .andDo(result -> System.out.println(result.getResponse().getContentAsString()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.data").value("USER_UPDATED"));
//    }
}
