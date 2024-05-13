package com.example.movieapp.service;

import com.example.movieapp.entity.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getCommentsOfBlog(Integer id);
}
