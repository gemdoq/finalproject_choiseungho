package com.example.finalproject_choiseungho.repository;

import com.example.finalproject_choiseungho.domain.entity.Post;
import com.example.finalproject_choiseungho.domain.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAll(Pageable pageable);

    Page<Post> findAllByUser(User user, Pageable pageable);
}