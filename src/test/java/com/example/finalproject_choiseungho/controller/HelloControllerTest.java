package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.service.AlgorithmService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @MockBean
    AlgorithmService algorithmService;

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithMockUser
    void sumOfDigits() throws Exception {
        when(algorithmService.sumOfDigits(any(Integer.TYPE))).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/hello/1234").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(10)));
    }
}