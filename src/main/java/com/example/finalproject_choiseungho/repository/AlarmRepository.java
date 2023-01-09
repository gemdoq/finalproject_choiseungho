package com.example.finalproject_choiseungho.repository;

import com.example.finalproject_choiseungho.domain.entity.Alarm;
import com.example.finalproject_choiseungho.domain.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {
    List<Alarm> findAllByUser(User user, Pageable pageable);
}