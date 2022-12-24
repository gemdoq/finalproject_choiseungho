package com.example.finalproject_choiseungho.service;

import com.example.finalproject_choiseungho.domain.dto.PostCreateRequest;
import com.example.finalproject_choiseungho.domain.dto.PostDto;
import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.domain.entity.User;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.UserException;
import com.example.finalproject_choiseungho.repository.PostRepository;
import com.example.finalproject_choiseungho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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
}