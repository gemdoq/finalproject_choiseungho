package com.example.finalproject_choiseungho.service;

import com.example.finalproject_choiseungho.domain.dto.AlarmReadResponse;
import com.example.finalproject_choiseungho.domain.entity.Alarm;
import com.example.finalproject_choiseungho.domain.entity.User;
import com.example.finalproject_choiseungho.exception.ErrorCode;
import com.example.finalproject_choiseungho.exception.UserException;
import com.example.finalproject_choiseungho.repository.AlarmRepository;
import com.example.finalproject_choiseungho.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {

    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    public List<AlarmReadResponse> readAllAlarmList(Pageable pageable, Authentication authentication) {
        User user = userRepository.findByUserName(authentication.getName())
                .orElseThrow(() -> new UserException(ErrorCode.USERNAME_NOT_FOUND, ErrorCode.USERNAME_NOT_FOUND.getMessage()));
        log.info("authenticated name", authentication.getName());

        List<Alarm> alarmList = alarmRepository.findAllByUser(user, pageable);
        return alarmList.stream().map(AlarmReadResponse::toAlarmReadResponse).collect(Collectors.toList());
    }
}