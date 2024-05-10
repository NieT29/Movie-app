package com.example.movieapp.repository;

import com.example.movieapp.entity.Blog;
import com.example.movieapp.entity.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Page<Blog> findByStatusOrderByCreatedAtDesc(Boolean status, Pageable pageable);
    Blog findByIdAndSlugAndStatus(Integer id, String slug, Boolean status);

}
