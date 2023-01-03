package com.example.finalproject_choiseungho.repository;

import com.example.finalproject_choiseungho.domain.entity.Comment;
import com.example.finalproject_choiseungho.domain.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByPost(Post post, Pageable pageable);
}