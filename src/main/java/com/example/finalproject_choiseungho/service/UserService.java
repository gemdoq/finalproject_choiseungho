package com.example.finalproject_choiseungho.service;

import com.example.finalproject_choiseungho.domain.dto.UserDto;
import com.example.finalproject_choiseungho.domain.dto.UserJoinRequest;
import com.example.finalproject_choiseungho.domain.entity.User;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.UserException;
import com.example.finalproject_choiseungho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto join(UserJoinRequest userJoinRequest) {
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user-> {
                    throw new UserException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserName:%s", userJoinRequest.getUserName()));
                });

        User savedUser = userRepository.save(userJoinRequest.toUser(bCryptPasswordEncoder.encode(userJoinRequest.getPassword())));
        return savedUser.toUserDto();
    }
}