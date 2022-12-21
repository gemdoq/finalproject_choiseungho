package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.UserDto;
import com.example.finalproject_choiseungho.domain.dto.UserJoinRequest;
import com.example.finalproject_choiseungho.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @Test
    @DisplayName("User join success")
    void join_success() throws Exception {
        when(userService.join(any(UserJoinRequest.class))).thenReturn(mock(UserDto.class));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/users/join")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(mockUserJoinRequest)))
                .andExpect(status().isOk());

        verify(userService).join(any(UserJoinRequest.class));
    }
}