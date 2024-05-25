package com.example.movieapp.service;

import com.example.movieapp.entity.Blog;
import com.example.movieapp.model.request.UpsertBlogRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Page<Blog> getBlogByType(Boolean status, int page, int pageSize);
    Blog getBlog(Integer id, String slug, Boolean status);
    List<Blog> getAllBlogs();
    List<Blog> getOwnBlogs();

    Blog createBlog(UpsertBlogRequest request);

    Blog getBlogById(Integer id);

    Blog updateBlog(Integer id, UpsertBlogRequest request);

    void deleteBlog(Integer id);

    String uploadThumbnail(Integer id, MultipartFile file);
}
