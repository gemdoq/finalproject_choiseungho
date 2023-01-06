package com.example.finalproject_choiseungho.repository;

import com.example.finalproject_choiseungho.domain.entity.Jowayo;
import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JowayoRepository extends JpaRepository<Jowayo, Long> {
    Optional<Jowayo> findByPostAndUser(Post post, User user);
    Integer countByPost(Post post);
}