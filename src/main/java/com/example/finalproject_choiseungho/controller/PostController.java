package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.*;
import com.example.finalproject_choiseungho.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @PostMapping
    public Response<PostCreateResponse> createPost(@RequestBody PostCreateRequest postCreateRequest, Authentication authentication) {
        log.info("PostCreateRequest's title : {}, body : {}", postCreateRequest.getTitle(), postCreateRequest.getBody());
        log.info("Authentication's ", authentication);

        PostDto savedPostDto = postService.createPost(postCreateRequest, authentication);
        return Response.success(new PostCreateResponse("포스트 등록 완료", savedPostDto.getId()));
    }

    @GetMapping
    public Response<Page<PostReadResponse>> readAllPostList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<PostReadResponse> postList = postService.readAllPostList(pageable);
        return Response.success(postList);
    }

    @GetMapping("/{postId}")
    public Response<PostReadResponse> readOnePost(@PathVariable Long postId) {
        log.info("Post id : " + postId);
        return Response.success(postService.readOnePost(postId));
    }

    @PutMapping("/{postId}")
    public Response<PostUpdateResponse> updatePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest, Authentication authentication) {
        log.info("Post id : " + postId);
        log.info("PostUpdateRequest's title : {}, body : {}", postUpdateRequest.getTitle(), postUpdateRequest.getBody());
        log.info("Authentication's ", authentication);

        Long updatedPostId = postService.updatePost(postId, postUpdateRequest, authentication);
        return Response.success(new PostUpdateResponse("포스트 수정 완료", updatedPostId));
    }

    @DeleteMapping("/{postId}")
    public Response<PostDeleteResponse> deletePostById(@PathVariable Long postId, Authentication authentication) {
        log.info("Authentication's ", authentication);

        Long deletedPostId = postService.deletePostById(postId, authentication);
        return Response.success(new PostDeleteResponse("포스트 삭제 완료", deletedPostId));
    }
}