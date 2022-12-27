package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.configuration.SecurityConfig;
import com.example.finalproject_choiseungho.domain.dto.*;
import com.example.finalproject_choiseungho.domain.entity.UserRole;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.UserException;
import com.example.finalproject_choiseungho.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ImportAutoConfiguration(SecurityConfig.class)
@MockBean(JpaMetamodelMappingContext.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    UserService userService;

    UserJoinRequest mockUserJoinRequest = UserJoinRequest.builder()
            .userName("mockUserName")
            .password("mockPassword")
            .build();

    UserLoginRequest mockUserLoginRequest = UserLoginRequest.builder()
            .userName("mockUserName")
            .password("mockPassword")
            .build();

    @Test
    @DisplayName("User join success")
    @WithMockUser
    void join_success() throws Exception {
        when(userService.join(any(UserJoinRequest.class))).thenReturn(new UserDto(1L, "mockUserName", "mockPassword", UserRole.USER));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUserJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result.userId").value(1L))
                .andExpect(jsonPath("$.result.userName").value("mockUserName"));


        verify(userService).join(any(UserJoinRequest.class));
    }

    @Test
    @DisplayName("User join failure")
    @WithMockUser
    void join_failure() throws Exception {
        when(userService.join(any(UserJoinRequest.class))).thenThrow(new UserException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUserJoinRequest)))
                .andDo(print())
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$.resultCode").value("DUPLICATED_USER_NAME"));

        verify(userService).join(any(UserJoinRequest.class));
    }

    @Test
    @DisplayName("User login success")
    @WithMockUser
    void login_success() throws Exception {
        when(userService.login(any(UserLoginRequest.class))).thenReturn(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUserLoginRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.resultCode").value("SUCCESS"))
                .andExpect(jsonPath("$.result.jwt").exists());

        verify(userService).login(any(UserLoginRequest.class));
    }

    @Test
    @DisplayName("User login failure - User not found")
    @WithMockUser
    void login_failure_userName() throws Exception {
        when(userService.login(any(UserLoginRequest.class))).thenThrow(new UserException(ErrorCode.USERNAME_NOT_FOUND, ""));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUserLoginRequest)))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.resultCode").value("USERNAME_NOT_FOUND"));

        verify(userService).login(any(UserLoginRequest.class));
    }

    @Test
    @DisplayName("User login failure - Invalid password")
    @WithMockUser
    void login_failure_password() throws Exception {
        when(userService.login(any(UserLoginRequest.class))).thenThrow(new UserException(ErrorCode.INVALID_PASSWORD, ""));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUserLoginRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PASSWORD.getHttpStatus().value()));

        verify(userService).login(any(UserLoginRequest.class));
    }
}