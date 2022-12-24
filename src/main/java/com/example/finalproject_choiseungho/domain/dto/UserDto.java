package com.example.finalproject_choiseungho.domain.dto;

import com.example.finalproject_choiseungho.domain.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDto {
    private Long id;
    private String userName;
    private String password;
    private UserRole role;
}