package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.*;
import com.example.finalproject_choiseungho.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping
    public Response<Page<PostReadResponse>> readAllPostList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<PostReadResponse> postList = postService.readAllPostList(pageable);
        return Response.success(postList);
    }
}