package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.service.AlgorithmService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class HelloController {

    private final AlgorithmService algorithmService;

    @ApiOperation(value = "특정 문자열 출력 기능", notes = "특정 문자열 출력 여부 확인용")
    @GetMapping("/api/v1/hello")
    public String hello() {
        return "최승호";
    }

    @ApiOperation(value = "입력받은 수의 자릿수의 합을 구하는 기능", notes = "수를 입력하면 해당 수의 자릿수를 모두 더한 정수를 출력")
    @GetMapping("/api/v1/hello/{num}")
    public int sumOfDigits(@ApiParam("자릿수를 모두 더하고 싶은 정수") @PathVariable Integer num) {
        log.info("인수로 전달받은 수 : " + num.toString());

        int answer = algorithmService.sumOfDigits(num);
        log.info("AlgorithmService.sumOfDigits에서 전달받은 answer : " + answer);

        return answer;
    }
}