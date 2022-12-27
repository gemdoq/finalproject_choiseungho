package com.example.finalproject_choiseungho.service;

import com.example.finalproject_choiseungho.domain.dto.PostCreateRequest;
import com.example.finalproject_choiseungho.domain.dto.PostUpdateRequest;
import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.domain.entity.User;
import com.example.finalproject_choiseungho.domain.entity.UserRole;
import com.example.finalproject_choiseungho.repository.PostRepository;
import com.example.finalproject_choiseungho.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class PostServiceTest {

    private final PostRepository postRepository = mock(PostRepository.class);
    private final UserRepository userRepository = mock(UserRepository.class);

    PostService postService;
    User user;

    PostCreateRequest postCreateRequest;
    PostUpdateRequest postUpdateRequest;
    Post post;

    @BeforeEach
    void setUp() {
        postService = new PostService(postRepository, userRepository);
        user = User.builder()
                .id(1L)
                .userName("mockUserName")
                .password("mockPassword")
                .role(UserRole.USER)
                .build();
        postCreateRequest = new PostCreateRequest("mockTitle", "mockBody");
        postUpdateRequest = new PostUpdateRequest("mockTitle", "mockBody");
        post = Post.builder()
                .id(1L)
                .title(postCreateRequest.getTitle())
                .body(postCreateRequest.getBody())
                .user(user)
                .build();
    }

    @Test
    @DisplayName("포스트 등록 성공")
    void postCreate_success() {
        User mockUser = mock(User.class);
        Post mockPost = mock(Post.class);

        Mockito.when(postRepository.save(postCreateRequest.toPost(any()))).thenReturn(post);

        Assertions.assertDoesNotThrow(()-> postService.createPost(postCreateRequest, any()));
    }
}