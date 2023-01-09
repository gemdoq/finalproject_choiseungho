package com.example.finalproject_choiseungho.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class AlarmList {
    private List<AlarmReadResponse> content;
}