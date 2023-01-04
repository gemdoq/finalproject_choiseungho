package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.*;
import com.example.finalproject_choiseungho.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/{postId}/comments")
    public Response<CommentCreateResponse> createComment(@PathVariable Long postId, @RequestBody CommentCreateRequest commentCreateRequest, Authentication authentication) {
        log.info("Post Id : " + postId);
        log.info("Comment Create Request's comment : " + commentCreateRequest.getComment());
        log.info("Authentication : " + authentication);

        CommentDto savedCommentDto = commentService.createComment(postId, commentCreateRequest, authentication);
        return Response.success(savedCommentDto.toCommentCreateResponse());
    }

    @GetMapping("/{postId}/comments")
    public Response<Page<CommentReadResponse>> readAllCommentList(@PathVariable Long postId, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentReadResponse> commentList = commentService.readAllCommentList(postId, pageable);
        log.info("Post Id : " + postId);
        log.info("pageable : {}", pageable.toString());
        log.info("Page<PostReadResponse> : {}", commentList.toString());

        return Response.success(commentList);
    }

    @GetMapping("/{postId}/comments/{commentId}")
    public Response<CommentReadResponse> readOneComment(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId) {
        log.info("Post id : {}, Comment id : {}", postId, commentId);
        return Response.success(commentService.readOneComment(postId, commentId));
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public Response<CommentUpdateResponse> updateComment(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest, Authentication authentication) {
        log.info("Post id : {}, Comment id : {}", postId, commentId);
        log.info("CommentUpdateRequest's comment : " + commentUpdateRequest.getComment());
        log.info("Authentication : " + authentication);

        CommentDto updatedCommentDto = commentService.updateComment(postId, commentId, commentUpdateRequest, authentication);
        return Response.success(updatedCommentDto.toCommentUpdateResponse());
    }

    @DeleteMapping("/{postId}/comments/{commentId}")
    public Response<CommentDeleteResponse> deleteCommentById(@PathVariable(value = "postId") Long postId, @PathVariable(value = "commentId") Long commentId, Authentication authentication) {
        log.info("Post id : {}, Comment id : {}", postId, commentId);
        log.info("Authentication : " + authentication);

        Long deletedCommentId = commentService.deleteCommentById(postId, commentId, authentication);
        return Response.success(new CommentDeleteResponse("댓글 삭제 완료", deletedCommentId));
    }
}