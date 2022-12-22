package com.example.finalproject_choiseungho.service;

import com.example.finalproject_choiseungho.domain.dto.UserDto;
import com.example.finalproject_choiseungho.domain.dto.UserJoinRequest;
import com.example.finalproject_choiseungho.domain.dto.UserLoginRequest;
import com.example.finalproject_choiseungho.domain.entity.User;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.UserException;
import com.example.finalproject_choiseungho.repository.UserRepository;
import com.example.finalproject_choiseungho.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${jwt.secret}")
    private String secretKey;
    private long expiredTimeMs = 1000 * 60 * 60;

    public UserDto join(UserJoinRequest userJoinRequest) {
        userRepository.findByUserName(userJoinRequest.getUserName())
                .ifPresent(user-> {
                    throw new UserException(ErrorCode.DUPLICATED_USER_NAME, String.format("UserName : %s", userJoinRequest.getUserName()));
                });

        User savedUser = userRepository.save(userJoinRequest.toUser(bCryptPasswordEncoder.encode(userJoinRequest.getPassword())));
        return savedUser.toUserDto();
    }

    public String login(UserLoginRequest userLoginRequest) {
        User user = userRepository.findByUserName(userLoginRequest.getUserName())
                .orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, String.format("UserName %s is not registered", userLoginRequest.getUserName())));

        if(!bCryptPasswordEncoder.matches(userLoginRequest.getPassword(), user.getPassword())) {
            throw new UserException(ErrorCode.INVALID_PASSWORD, String.format("Incorrect password entered : %s", userLoginRequest.getPassword()));
        }

        String jwt = JwtUtil.generateToken(userLoginRequest.getUserName(), secretKey, expiredTimeMs);
        return jwt;
    }
}