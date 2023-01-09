package com.example.finalproject_choiseungho.service;

import com.example.finalproject_choiseungho.domain.dto.*;
import com.example.finalproject_choiseungho.domain.entity.Alarm;
import com.example.finalproject_choiseungho.domain.entity.Comment;
import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.domain.entity.User;
import com.example.finalproject_choiseungho.exception.CommentException;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.PostException;
import com.example.finalproject_choiseungho.exception.UserException;
import com.example.finalproject_choiseungho.repository.AlarmRepository;
import com.example.finalproject_choiseungho.repository.CommentRepository;
import com.example.finalproject_choiseungho.repository.PostRepository;
import com.example.finalproject_choiseungho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final AlarmRepository alarmRepository;

    public CommentDto createComment(Long postId, CommentCreateRequest commentCreateRequest, Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        log.info("authenticated name", authentication.getName());

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Post {} is found", postId);

        Comment savedComment = commentRepository.save(commentCreateRequest.toComment(user, post));
        alarmRepository.save(Alarm.toAlarm(AlarmType.NEW_COMMENT_ON_POST, post, user));
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

    @Transactional
    public CommentDto updateComment(Long postId, Long commentId, CommentUpdateRequest commentUpdateRequest, Authentication authentication) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Got postId from PathVariable : " + postId);
        log.info("Post author UserName : {}", post.getUser().getUserName());

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));

        if(!isValid(comment, authentication)) throw new CommentException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());

        comment.update(commentUpdateRequest.getComment());

        return comment.toCommentDto();
    }

    public Long deleteCommentById(Long postId, Long commentId, Authentication authentication) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Got postId from PathVariable : " + postId);
        log.info("Post author UserName : {}", post.getUser().getUserName());

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new CommentException(ErrorCode.COMMENT_NOT_FOUND, ErrorCode.COMMENT_NOT_FOUND.getMessage()));

        if(!isValid(comment, authentication)) throw new CommentException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());

        commentRepository.delete(comment);

        return commentId;
    }

    public boolean isValid(Comment comment, Authentication authentication) {
        return comment.getUser().getUserName().equals(authentication.getName());
    }
}