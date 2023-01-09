package com.example.finalproject_choiseungho.domain.dto;

import com.example.finalproject_choiseungho.domain.entity.Alarm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
@Builder
public class AlarmReadResponse {
    private Long id;
    private AlarmType alarmType;
    private Long fromUserId;
    private Long targetId;
    private String text;
    private LocalDateTime createdAt;

    public static AlarmReadResponse toAlarmReadResponse(Alarm alarm) {
        return AlarmReadResponse.builder()
                .id(alarm.getId())
                .alarmType(alarm.getAlarmType())
                .fromUserId(alarm.getUser().getId())
                .targetId(alarm.getPost().getId())
                .text(alarm.getAlarmType().getAlarmText())
                .createdAt(alarm.getCreatedAt())
                .build();
    }
}