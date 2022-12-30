package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.CommentCreateRequest;
import com.example.finalproject_choiseungho.domain.dto.CommentCreateResponse;
import com.example.finalproject_choiseungho.domain.dto.Response;
import com.example.finalproject_choiseungho.service.CommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comments")
    public Response<CommentCreateResponse> createComment(@PathVariable Long postId, @RequestBody CommentCreateRequest commentCreateRequest, Authentication authentication) {
        log.info("Post Id : " + postId);
        log.info("Comment Create Request's comment : " + commentCreateRequest.)
    }
}