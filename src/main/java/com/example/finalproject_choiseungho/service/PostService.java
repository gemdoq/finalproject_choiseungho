package com.example.finalproject_choiseungho.service;

import com.example.finalproject_choiseungho.domain.dto.PostCreateRequest;
import com.example.finalproject_choiseungho.domain.dto.PostDto;
import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    public PostDto createPost(PostCreateRequest postCreateRequest) {
        Post savedPost = postRepository.save(postCreateRequest.toPost());
        return savedPost.toPostDto();
    }
}