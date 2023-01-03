package com.example.finalproject_choiseungho.service;

import com.example.finalproject_choiseungho.domain.dto.CommentCreateRequest;
import com.example.finalproject_choiseungho.domain.dto.CommentDto;
import com.example.finalproject_choiseungho.domain.dto.CommentReadResponse;
import com.example.finalproject_choiseungho.domain.entity.Comment;
import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.domain.entity.User;
import com.example.finalproject_choiseungho.exception.CommentException;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.PostException;
import com.example.finalproject_choiseungho.exception.UserException;
import com.example.finalproject_choiseungho.repository.CommentRepository;
import com.example.finalproject_choiseungho.repository.PostRepository;
import com.example.finalproject_choiseungho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public CommentDto createComment(Long postId, CommentCreateRequest commentCreateRequest, Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        log.info("authenticated name", authentication.getName());

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Post {} is found", postId);

        Comment savedComment = commentRepository.save(commentCreateRequest.toComment(user, post));
        return savedComment.toCommentDto();
    }

    public Page<CommentReadResponse> readAllCommentList(Long postId, Pageable pageable) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Post {} is found", postId);
        return commentRepository.findByPost(post, pageable).map(CommentReadResponse::toCommentReadResponse);
    }

    public CommentReadResponse readOneComment(Long postId, Long commentId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Post {} is found", postId);

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));
        log.info("Comment {} is found", commentId);

        return CommentReadResponse.toCommentReadResponse(comment);
    }
}