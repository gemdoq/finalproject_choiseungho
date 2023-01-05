package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.*;
import com.example.finalproject_choiseungho.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원 가입 기능", notes = "Request Body에 회원명과 암호를 작성")
    @PostMapping("/join")
    public Response<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserDto savedUserDto = userService.join(userJoinRequest);
        return Response.success(new UserJoinResponse(savedUserDto.getId(), savedUserDto.getUserName()));
    }

    @ApiOperation(value = "회원 로그인 기능", notes = "Request Body에 회원명과 암호를 작성")
    @PostMapping("/login")
    public Response<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        String jwt = userService.login(userLoginRequest);
        return Response.success(new UserLoginResponse(jwt));
    }
}