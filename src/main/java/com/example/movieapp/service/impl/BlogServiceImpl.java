package com.example.movieapp.service.impl;

import com.example.movieapp.entity.Blog;
import com.example.movieapp.entity.Review;
import com.example.movieapp.entity.User;
import com.example.movieapp.exception.BadRequestException;
import com.example.movieapp.exception.ResourceNotFoundException;
import com.example.movieapp.model.request.UpsertBlogRequest;
import com.example.movieapp.repository.BlogRepository;
import com.example.movieapp.security.CustomUserDetails;
import com.example.movieapp.service.BlogService;
import com.example.movieapp.service.FileService;
import com.github.slugify.Slugify;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogRepository blogRepository;
    @Autowired
    private HttpSession session;
    @Autowired
    private FileService fileService;

    @Override
    public Page<Blog> getBlogByType(Boolean status, int page, int pageSize) {
        PageRequest pageRequest = PageRequest.of(page -1, pageSize);
        return blogRepository.findByStatusOrderByCreatedAtDesc(status, pageRequest);
    }

    public Blog getBlog(Integer id, String slug, Boolean status) {
        return blogRepository.findByIdAndSlugAndStatus(id, slug, status);
    }

    @Override
    public List<Blog> getAllBlogs() {
        return blogRepository.findAll(Sort.by("createdAt").descending());
    }

    @Override
    public List<Blog> getOwnBlogs() {
        // TODO: sử dụng securityContexHolder để lấy thông tin user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        return blogRepository.findByUser_IdOrderByCreatedAtDesc(currentUser.getId());
    }

    @Override
    public Blog getBlogById(Integer id) {
        Optional<Blog> optionalBlog = blogRepository.findById(id);
        return optionalBlog.orElse(null);
    }

    @Override
    public Blog createBlog(UpsertBlogRequest request) {
        // TODO: sử dụng securityContexHolder để lấy thông tin user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();
        if (currentUser == null) {
            throw new BadRequestException("User must be logged in to create a blog post");
        }
        Slugify slugify= Slugify.builder().build();
        Blog blog = Blog.builder()
                .title(request.getTitle())
                .slug(slugify.slugify(request.getTitle()))
                .content(request.getContent())
                .thumbnail("https://placehold.co/600x400?text=" + String.valueOf(request.getTitle().charAt(0)).toUpperCase())
                .description(request.getDescription())
                .status(request.getStatus())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .user(currentUser)
                .build();
        blogRepository.save(blog);
        return blog;
    }

    @Override
    public Blog updateBlog(Integer id, UpsertBlogRequest request) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));

        // TODO: sử dụng securityContexHolder để lấy thông tin user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        if (currentUser == null) {
            throw new BadRequestException("User must be logged in to update a blog post");
        }

        if (!blog.getUser().getId().equals(currentUser.getId())) {
            throw new BadRequestException("User are not allowed to update other people's blogs");
        }
        Slugify slugify = Slugify.builder().build();
        blog.setTitle(request.getTitle());
        blog.setSlug(slugify.slugify(blog.getTitle()));
        blog.setContent(request.getContent());
        blog.setDescription(request.getDescription());
        blog.setStatus(request.getStatus());
        blog.setUpdatedAt(LocalDateTime.now());
        blogRepository.save(blog);
        return blog;
    }

    @Override
    public void deleteBlog(Integer id) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));

        // TODO: sử dụng securityContexHolder để lấy thông tin user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        User currentUser = userDetails.getUser();

        if (currentUser == null) {
            throw new BadRequestException("User must be logged in to delete a blog post");
        }

        if (!blog.getUser().getId().equals(currentUser.getId())) {
            throw new BadRequestException("User are not allowed to delete other people's blogs");
        }
        blogRepository.delete(blog);
    }

    @Override
    public String uploadThumbnail(Integer id, MultipartFile file) {
        Blog blog = blogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Blog not found"));

        try {
            Map data = fileService.uploadFile(file);
            String url = (String) data.get("url");
            blog.setThumbnail(url);
            blogRepository.save(blog);

            return url;
        } catch (IOException e) {
            throw  new RuntimeException("Error uploading file");
        }
    }

    @Override
    public List<Blog> getBlogsCreatedByMonth() {
        LocalDateTime startDate = LocalDate.now().withDayOfMonth(1).atStartOfDay();
        LocalDateTime endDate = LocalDateTime.now();
        return blogRepository.findByCreatedAtBetween(startDate, endDate);
    }

}
