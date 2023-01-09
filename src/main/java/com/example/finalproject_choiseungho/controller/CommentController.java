package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.*;
import com.example.finalproject_choiseungho.service.CommentService;
import io.swagger.annotations.Api;
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
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "댓글(COMMENT)")
@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @ApiOperation(value = "댓글 작성 기능", notes = "Parameter의 postId에 포스트 ID를 넣고, Request body의 comment를 작성")
    @PostMapping("/{postId}/comments")
    public Response<CommentCreateResponse> createComment(@ApiParam("포스트 ID") @PathVariable Long postId, @RequestBody CommentCreateRequest commentCreateRequest, @ApiIgnore Authentication authentication) {
        log.info("Post Id : " + postId);
        log.info("Comment Create Request's comment : " + commentCreateRequest.getComment());
        log.info("Authentication : " + authentication);

        CommentDto savedCommentDto = commentService.createComment(postId, commentCreateRequest, authentication);
        return Response.success(savedCommentDto.toCommentCreateResponse());
    }

    @ApiOperation(value = "전체 댓글 조회 기능", notes = "Parameter의 postId에 포스트 ID 입력")
    @GetMapping("/{postId}/comments")
    public Response<Page<CommentReadResponse>> readAllCommentList(@ApiParam("포스트 ID") @PathVariable Long postId, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CommentReadResponse> commentList = commentService.readAllCommentList(postId, pageable);
        log.info("Post Id : " + postId);
        log.info("pageable : {}", pageable.toString());
        log.info("Page<PostReadResponse> : {}", commentList.toString());

        return Response.success(commentList);
    }

    @ApiOperation(value = "단일 댓글 조회 기능", notes = "Parameter의 postId에 포스트 ID, commentId에 댓글 ID 입력")
    @GetMapping("/{postId}/comments/{commentId}")
    public Response<CommentReadResponse> readOneComment(@ApiParam("포스트 ID") @PathVariable(value = "postId") Long postId, @ApiParam("댓글 ID") @PathVariable(value = "commentId") Long commentId) {
        log.info("Post id : {}, Comment id : {}", postId, commentId);
        return Response.success(commentService.readOneComment(postId, commentId));
    }

    @ApiOperation(value = "댓글 수정 기능", notes = "Parameter의 postId에 포스트 ID, commentId에 댓글 ID 입력 후 Request body의 comment에 수정할 댓글 내용 작성")
    @PutMapping("/{postId}/comments/{commentId}")
    public Response<CommentUpdateResponse> updateComment(@ApiParam("포스트 ID") @PathVariable(value = "postId") Long postId, @ApiParam("댓글 ID") @PathVariable(value = "commentId") Long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest, @ApiIgnore Authentication authentication) {
        log.info("Post id : {}, Comment id : {}", postId, commentId);
        log.info("CommentUpdateRequest's comment : " + commentUpdateRequest.getComment());
        log.info("Authentication : " + authentication);

        CommentDto updatedCommentDto = commentService.updateComment(postId, commentId, commentUpdateRequest, authentication);
        return Response.success(updatedCommentDto.toCommentUpdateResponse());
    }

    @ApiOperation(value = "댓글 삭제 기능", notes = "Parameter의 postId에 포스트 ID, commentId에 댓글 ID 입력")
    @DeleteMapping("/{postId}/comments/{commentId}")
    public Response<CommentDeleteResponse> deleteCommentById(@ApiParam("포스트 ID") @PathVariable(value = "postId") Long postId, @ApiParam("댓글 ID") @PathVariable(value = "commentId") Long commentId, @ApiIgnore Authentication authentication) {
        log.info("Post id : {}, Comment id : {}", postId, commentId);
        log.info("Authentication : " + authentication);

        Long deletedCommentId = commentService.deleteCommentById(postId, commentId, authentication);
        return Response.success(new CommentDeleteResponse("댓글 삭제 완료", deletedCommentId));
    }
}