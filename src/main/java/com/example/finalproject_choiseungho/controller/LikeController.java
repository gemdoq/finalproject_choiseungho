package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.Response;
import com.example.finalproject_choiseungho.service.JowayoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final JowayoService jowayoService;

    @ApiOperation(value = "해당 포스트에 좋아요를 누르면 row가 추가되고, 이미 좋아요를 누른 경우 좋아요를 취소하는 기능", notes = "Parameter의 postId에 포스트 ID 입력")
    @PostMapping("/{postId}/likes")
    public Response<String> createJowayo(@ApiParam("포스트 ID") @PathVariable Long postId, @ApiIgnore Authentication authentication) {
        log.info("Post Id : " + postId);
        log.info("Authentication : " + authentication);

        Integer result = jowayoService.createJowayo(postId, authentication);
        if(result == 0) return Response.success("좋아요를 취소했습니다.");
        else return Response.success("좋아요를 눌렀습니다.");
    }

    @ApiOperation(value = "해당 포스트의 좋아요 개수 조회하는 기능", notes = "Parameter의 postId에 포스트 ID 입력")
    @GetMapping("/{postId}/likes")
    public Response<Integer> countJowayo(@ApiParam("포스트 ID") @PathVariable Long postId) {
        log.info("Post Id : " + postId);

        return Response.success(jowayoService.countJowayo(postId));
    }
}