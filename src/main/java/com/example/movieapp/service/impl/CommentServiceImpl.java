package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Comment;
import com.example.movieapp.repository.CommentRepository;
import com.example.movieapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;


    @Override
    public List<Comment> getCommentsOfBlog(Integer id) {
        return commentRepository.findByBlog_IdOrderByCreatedAtDesc(id);
    }
}
