package com.example.movieapp.rest;

import com.example.movieapp.entity.Blog;
import com.example.movieapp.model.request.UpsertBlogRequest;
import com.example.movieapp.service.BlogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/blogs")
public class BlogApi {
    private final BlogService blogService;

    @PostMapping
    public ResponseEntity<?> createBlog(@Valid @RequestBody UpsertBlogRequest request) {
        Blog blog = blogService.createBlog(request);
        return new ResponseEntity<>(blog, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBlog(@Valid @RequestBody UpsertBlogRequest request, @PathVariable Integer id) {
        Blog blog = blogService.updateBlog(id, request);
        return ResponseEntity.ok(blog);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBlog(@PathVariable Integer id) {
        blogService.deleteBlog(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/upload-thumbnail")
    public ResponseEntity<?> upLoadThumbnail(@PathVariable Integer id,
                                             @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(blogService.uploadThumbnail(id, file));
    }
}
