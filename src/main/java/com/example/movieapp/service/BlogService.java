package com.example.movieapp.service;

import com.example.movieapp.entity.Blog;
import org.springframework.data.domain.Page;

public interface BlogService {
    Page<Blog> getBlogByType(Boolean status, int page, int pageSize);
    Blog getBlog(int id, String slug, Boolean status);
}
