package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Blog;
import com.example.movieapp.repository.BlogRepository;
import com.example.movieapp.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;

    @Override
    public Page<Blog> getBlogByType(Boolean status, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page -1, pageSize);
        return blogRepository.findByStatusOrderByCreatedAtDesc(status, pageRequest);
    }

    public Blog getBlog(Integer id, String slug, Boolean status) {
        return blogRepository.findByIdAndSlugAndStatus(id, slug, status);
    }
}
