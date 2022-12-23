package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.UserDto;
import com.example.finalproject_choiseungho.domain.dto.UserJoinRequest;
import com.example.finalproject_choiseungho.domain.dto.UserLoginRequest;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.UserException;
import com.example.finalproject_choiseungho.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
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
        when(userService.join(any(UserJoinRequest.class))).thenReturn(mock(UserDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUserJoinRequest)))
                .andDo(print())
                .andExpect(status().isOk());

        verify(userService).join(any(UserJoinRequest.class));
    }

    @Test
    @DisplayName("User join failure")
    @WithMockUser
    void join_failure() throws Exception {
        when(userService.join(any(UserJoinRequest.class))).thenThrow(new UserException(ErrorCode.DUPLICATED_USER_NAME, ""));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(mockUserJoinRequest)))
                .andDo(print())
                .andExpect(status().isConflict());

        verify(userService).join(any(UserJoinRequest.class));
    }

    @Test
    @DisplayName("User login success")
    @WithMockUser
    void login_success() throws Exception {
        when(userService.login(any(UserLoginRequest.class))).thenReturn(anyString());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                .with(csrf())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(mockUserLoginRequest)))
                .andExpect(status().isOk());

        verify(userService).login(any(UserLoginRequest.class));
    }

    @Test
    @DisplayName("User login failure")
    @WithMockUser
    void login_failure() throws Exception {
        when(userService.login(any(UserLoginRequest.class))).thenThrow(new UserException(ErrorCode.USERNAME_NOT_FOUND, ""));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockUserLoginRequest)))
                .andExpect(status().isNotFound());

        verify(userService).login(any(UserLoginRequest.class));
    }
}