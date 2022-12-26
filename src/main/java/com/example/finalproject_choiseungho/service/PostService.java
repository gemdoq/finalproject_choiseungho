package com.example.finalproject_choiseungho.service;

import com.example.finalproject_choiseungho.domain.dto.PostCreateRequest;
import com.example.finalproject_choiseungho.domain.dto.PostDto;
import com.example.finalproject_choiseungho.domain.dto.PostReadResponse;
import com.example.finalproject_choiseungho.domain.dto.PostUpdateRequest;
import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.domain.entity.User;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.PostException;
import com.example.finalproject_choiseungho.exception.UserException;
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
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostDto createPost(PostCreateRequest postCreateRequest, Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        log.info("authentication", authentication.toString());
        log.info("authenticated name", authentication.getName());

        Post savedPost = postRepository.save(postCreateRequest.toPost(user));
        return savedPost.toPostDto();
    }

    public Page<PostReadResponse> readAllPostList(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostReadResponse::toPostReadResponse);
    }

    public PostReadResponse readOnePost(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Got postId from PathVariable" + postId);

        return PostReadResponse.toPostReadResponse(post);
    }

    @Transactional
    public Long updatePost(Long postId, PostUpdateRequest postUpdateRequest, Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        log.info("authentication", authentication.toString());
        log.info("authenticated name", authentication.getName());

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Got postId from PathVariable : " + postId);
        log.info("Post author UserName : {}", post.getUser().getUserName());

        if( !post.getUser().equals(user)) throw new PostException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());

        post.update(postUpdateRequest.toPost(user));
        log.info("Updated post id : " + post.getId());

        return post.getId();
    }

    public Long deletePostById(Long postId, Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        log.info("authentication", authentication.toString());
        log.info("authenticated name", authentication.getName());

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Got postId from PathVariable" + postId);
        log.info("Post author UserName : {}", post.getUser().getUserName());

        if( !post.getUser().equals(user)) throw new PostException(ErrorCode.INVALID_PERMISSION, ErrorCode.INVALID_PERMISSION.getMessage());

        postRepository.deleteById(postId);

        return postId;
    }
}