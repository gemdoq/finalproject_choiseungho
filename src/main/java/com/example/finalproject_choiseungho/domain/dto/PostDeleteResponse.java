package com.example.finalproject_choiseungho.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PostDeleteResponse {
    private String message;
    private Long postId;
}