package com.example.movieapp.repository;

import com.example.movieapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByBlog_IdOrderByCreatedAtDesc(Integer blogId);
}
