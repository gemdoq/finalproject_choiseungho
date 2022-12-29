package com.example.finalproject_choiseungho.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlgorithmService {

    public int sumOfDigits(Integer num) {
        log.info("인수로 전달받은 숫자 : " + num);
        int answer = 0;

        while(num!=0){
            answer += num%10;
            num /= 10;
        }

        log.info("각 자릿수의 합 : " + answer);
        return answer;
    }
}
