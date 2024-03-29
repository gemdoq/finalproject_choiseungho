package com.example.finalproject_choiseungho.domain.dto;

import com.example.finalproject_choiseungho.domain.entity.Post;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class PostReadResponse {
    private Long id;
    private String title;
    private String body;
    private String userName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime lastModifiedAt;

    public PostReadResponse(Long id, String title, String body, String userName) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userName = userName;
    }

    public static PostReadResponse toPostReadResponse(Post post) {
        return PostReadResponse.builder()
                .id(post.getId())
                .title(post.getTitle())
                .body(post.getBody())
                .userName(post.getUser().getUserName())
                .createdAt(post.getCreatedAt())
                .lastModifiedAt(post.getLastModifiedAt())
                .build();
    }
}