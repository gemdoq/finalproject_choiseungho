package com.example.finalproject_choiseungho.domain.dto;

import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class CommentDto {
    private Long id;
    private String comment;
    private Post post;
    private User user;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;


    public CommentCreateResponse toCommentCreateResponse() {
        return CommentCreateResponse.builder()
                .id(this.id)
                .comment(this.comment)
                .userName(this.user.getUserName())
                .postId(this.post.getId())
                .createdAt(this.createdAt)
                .build();
    }
}