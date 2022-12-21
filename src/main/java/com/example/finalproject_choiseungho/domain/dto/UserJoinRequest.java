package com.example.finalproject_choiseungho.domain.dto;

import com.example.finalproject_choiseungho.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder // for TEST
public class UserJoinRequest {
    private String userName;
    private String password;

    public User toUser(String password) {
        return User.builder()
                .userName(this.userName)
                .password(password)
                .build();
    }
}