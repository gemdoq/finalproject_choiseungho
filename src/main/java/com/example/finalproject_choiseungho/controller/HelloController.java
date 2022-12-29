package com.example.finalproject_choiseungho.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/api/v1/hello")
    public String hello() {
        return "최승호";
    }

    @GetMapping("/api/v1/hello/{num}")
    public int sumOfDigits(@PathVariable Integer num) {
        int answer = 0;

        while(num!=0){
            answer += num%10;
            num /= 10;
        }

        return answer;
    }
}