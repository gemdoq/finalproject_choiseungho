package com.example.finalproject_choiseungho.controller;

import com.example.finalproject_choiseungho.service.AlgorithmService;
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

    @GetMapping("/api/v1/hello")
    public String hello() {
        return "최승호";
    }

    @GetMapping("/api/v1/hello/{num}")
    public int sumOfDigits(@PathVariable Integer num) {
        log.info("인수로 전달받은 수 : " + num.toString());

        int answer = algorithmService.sumOfDigits(num);
        log.info("AlgorithmService.sumOfDigits에서 전달받은 answer : " + answer);

        return answer;
    }
}