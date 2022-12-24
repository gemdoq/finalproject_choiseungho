package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.PostCreateRequest;
import com.example.finalproject_choiseungho.domain.dto.PostCreateResponse;
import com.example.finalproject_choiseungho.domain.dto.PostDto;
import com.example.finalproject_choiseungho.domain.dto.Response;
import com.example.finalproject_choiseungho.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<PostCreateResponse> createPost(@RequestBody PostCreateRequest postCreateRequest, Authentication authentication) {
        log.info("PostCreateRequest's title", postCreateRequest.getTitle());
        log.info("PostCreateRequest's body", postCreateRequest.getBody());
        log.info("Authentication's ", authentication);

        PostDto savedPostDto = postService.createPost(postCreateRequest, authentication);
        return Response.success(new PostCreateResponse("포스트 등록 완료", savedPostDto.getId()));
    }
}