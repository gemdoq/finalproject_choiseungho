package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.configuration.SecurityConfig;
import com.example.finalproject_choiseungho.domain.dto.PostCreateRequest;
import com.example.finalproject_choiseungho.domain.dto.PostDto;
import com.example.finalproject_choiseungho.domain.dto.PostReadResponse;
import com.example.finalproject_choiseungho.domain.dto.PostUpdateRequest;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.PostException;
import com.example.finalproject_choiseungho.exception.UserException;
import com.example.finalproject_choiseungho.service.PostService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {

    @MockBean
    PostService postService;

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    PostCreateRequest mockPostCreateRequest = new PostCreateRequest("mockTitle", "mockBody");
    PostUpdateRequest postUpdateRequest = new PostUpdateRequest("mockTitle", "mockBody");
    PostDto postDto = PostDto.builder()
            .id(1L)
            .title("mockTitle")
            .body("mockBody")
            .build();

    @DisplayName("Post create success")
    @WithMockUser
    @Test
    void createPost_success() throws Exception {
        when(postService.createPost(any(), any())).thenReturn(PostDto.builder()
                .id(1L)
                .title("mockTitle")
                .body("mockBody")
                .build());

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockPostCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").value("포스트 등록 완료"));
    }

    @DisplayName("Post create failure - not authenticated")
    @WithAnonymousUser
    @Test
    void createPost_fail() throws Exception {
        when(postService.createPost(any(), any())).thenThrow(new UserException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/posts")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(mockPostCreateRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    @DisplayName("Post read success")
    @WithMockUser
    @Test
    void readOnePost_success() throws Exception {
        when(postService.readOnePost(1L)).thenReturn(new PostReadResponse(1L, "mockTitle", "mockBody", "mockUser"));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/posts/1").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.id").value(postDto.getId()))
                .andExpect(jsonPath("$.result.title").value(postDto.getTitle()))
                .andExpect(jsonPath("$.result.body").value(postDto.getBody()));
    }

    @DisplayName("Post update success")
    @WithMockUser
    @Test
    void updatePost_success() throws Exception {
        when(postService.updatePost(any(), any(), any())).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postUpdateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").value("포스트 수정 완료"))
                .andExpect(jsonPath("$.result.postId").value(1));
    }

    @DisplayName("Post update failure")
    @WithAnonymousUser
    @Test
    void updatePost_fail() throws Exception {
        when(postService.updatePost(any(), any(), any()))
                .thenThrow(new PostException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/posts/1")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(postUpdateRequest)))
                .andDo(print())
                .andExpect(status().is(ErrorCode.INVALID_PERMISSION.getHttpStatus().value()));
    }

    @DisplayName("Post delete success")
    @WithMockUser
    @Test
    void deletePost_success() throws Exception {

        when(postService.deletePostById(any(), any())).thenReturn(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.result.message").value("포스트 삭제 완료"))
                .andExpect(jsonPath("$.result.postId").value(1));
    }

    @DisplayName("Post delete failure")
    @WithAnonymousUser
    @Test
    void deletePost_fail1() throws Exception {

        when(postService.deletePostById(any(), any()))
                .thenThrow(new PostException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage()));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/posts/1")
                        .with(csrf()))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }
}