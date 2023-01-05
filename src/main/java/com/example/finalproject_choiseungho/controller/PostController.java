package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.*;
import com.example.finalproject_choiseungho.service.PostService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    @ApiOperation(value = "포스트 작성 기능", notes = "Request body의 Title에 제목과 Body에 본문을 작성")
    @PostMapping
    public Response<PostCreateResponse> createPost(@RequestBody PostCreateRequest postCreateRequest, Authentication authentication) {
        log.info("PostCreateRequest's title : {}, body : {}", postCreateRequest.getTitle(), postCreateRequest.getBody());
        log.info("Authentication's ", authentication);

        PostDto savedPostDto = postService.createPost(postCreateRequest, authentication);
        return Response.success(new PostCreateResponse("포스트 등록 완료", savedPostDto.getId()));
    }

    @ApiOperation(value = "전체 포스트 조회 기능")
    @GetMapping
    public Response<Page<PostReadResponse>> readAllPostList(@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable) {
        Page<PostReadResponse> postList = postService.readAllPostList(pageable);
        log.info("pageable : {}", pageable.toString());
        log.info("Page<PostReadResponse> : {}", postList.toString());

        return Response.success(postList);
    }

    @ApiOperation(value = "단일 포스트 조회 기능", notes = "Parameter의 postId에 포스트 ID 입력")
    @GetMapping("/{postId}")
    public Response<PostReadResponse> readOnePost(@ApiParam("포스트 ID") @PathVariable Long postId) {
        log.info("Post id : " + postId);
        return Response.success(postService.readOnePost(postId));
    }

    @ApiOperation(value = "포스트 수정 기능", notes = "Parameter의 postId에 포스트 ID 입력 후 Request body의 Title에 제목과 Body에 본문을 작성")
    @PutMapping("/{postId}")
    public Response<PostUpdateResponse> updatePost(@ApiParam("포스트 ID") @PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest, Authentication authentication) {
        log.info("Post id : " + postId);
        log.info("PostUpdateRequest's title : {}, body : {}", postUpdateRequest.getTitle(), postUpdateRequest.getBody());
        log.info("Authentication's " + authentication);

        Long updatedPostId = postService.updatePost(postId, postUpdateRequest, authentication);
        return Response.success(new PostUpdateResponse("포스트 수정 완료", updatedPostId));
    }

    @ApiOperation(value = "포스트 삭제 기능", notes = "Parameter의 postId에 포스트 ID 입력")
    @DeleteMapping("/{postId}")
    public Response<PostDeleteResponse> deletePostById(@ApiParam("포스트 ID") @PathVariable Long postId, Authentication authentication) {
        log.info("Authentication's ", authentication);

        Long deletedPostId = postService.deletePostById(postId, authentication);
        return Response.success(new PostDeleteResponse("포스트 삭제 완료", deletedPostId));
    }
}