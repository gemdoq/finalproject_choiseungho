package com.example.finalproject_choiseungho.domain.dto;

import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.domain.entity.User;

import java.time.LocalDateTime;

public class JowayoCreateResponse {
    private Long id;
    private Post post;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
}