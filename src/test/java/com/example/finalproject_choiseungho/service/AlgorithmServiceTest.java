package com.example.finalproject_choiseungho.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AlgorithmServiceTest {

    AlgorithmService algorithmService = new AlgorithmService();

    @Test
    void sumOfDigits() {
        assertEquals(10, algorithmService.sumOfDigits(1234));
        assertEquals(15, algorithmService.sumOfDigits(12345));
        assertEquals(21, algorithmService.sumOfDigits(123456));
    }
}