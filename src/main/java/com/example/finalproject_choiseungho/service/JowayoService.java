package com.example.finalproject_choiseungho.service;

import com.example.finalproject_choiseungho.domain.dto.JowayoDto;
import com.example.finalproject_choiseungho.domain.entity.Jowayo;
import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.domain.entity.User;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.PostException;
import com.example.finalproject_choiseungho.exception.UserException;
import com.example.finalproject_choiseungho.repository.JowayoRepository;
import com.example.finalproject_choiseungho.repository.PostRepository;
import com.example.finalproject_choiseungho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class JowayoService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JowayoRepository jowayoRepository;

    public Integer createJowayo(Long postId, Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        log.info("authenticated name", authentication.getName());

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Post {} is found", postId);

        Optional<Jowayo> optionalJowayo = jowayoRepository.findByPostAndUser(post, user);
        if(optionalJowayo.isPresent()) {
            jowayoRepository.delete(optionalJowayo.get());
            return 0;
        } else {
            jowayoRepository.save(Jowayo.toJowayo(post, user));
            return 1;
        }
    }

    public Integer countJowayo(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new PostException(ErrorCode.POST_NOT_FOUND, ErrorCode.POST_NOT_FOUND.getMessage()));
        log.info("Post {} is found", postId);

        return jowayoRepository.countByPost(post);
    }
}