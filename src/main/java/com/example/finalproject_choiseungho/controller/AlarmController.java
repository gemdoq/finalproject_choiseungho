package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.domain.dto.AlarmList;
import com.example.finalproject_choiseungho.domain.dto.AlarmReadResponse;
import com.example.finalproject_choiseungho.domain.dto.Response;
import com.example.finalproject_choiseungho.service.AlarmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Api(tags = "알림(ALARM)")
@RestController
@RequestMapping("/api/v1/alarms")
@RequiredArgsConstructor
@Slf4j
public class AlarmController {

    private final AlarmService alarmService;

    @ApiOperation(value = "특정 유저의 알림 조회 기능")
    @GetMapping
    public Response<AlarmList> readAllAlarmList(
            @PageableDefault(
                    size = 20,
                    sort = "createdAt",
                    direction = Sort.Direction.DESC) Pageable pageable,
            @ApiIgnore Authentication authentication) {
        List<AlarmReadResponse> alarmList = alarmService.readAllAlarmList(pageable, authentication);
        log.info("AlarmList : {}", alarmList.toString());

        return Response.success(new AlarmList(alarmList));
    }
}