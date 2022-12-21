package com.example.finalproject_choiseungho.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String userName;
    private String password;

    public UserJoinResponse toUserJoinResponse() {
        return new UserJoinResponse(this.id, this.userName);
    }
}