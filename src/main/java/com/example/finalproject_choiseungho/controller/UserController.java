package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.UserDto;
import com.example.finalproject_choiseungho.domain.dto.UserJoinRequest;
import com.example.finalproject_choiseungho.domain.dto.UserJoinResponse;
import com.example.finalproject_choiseungho.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<UserJoinResponse> join(@RequestBody UserJoinRequest userJoinRequest) {
        UserDto userDto = userService.join(userJoinRequest);
        return ResponseEntity.ok(userDto.toUserJoinResponse());
    }
}