package com.example.movieapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String title;

    @Column(nullable = true)
    String slug;

    String description;

    @Column(nullable = false)
    String content;

    String thumbnail;

    Boolean status;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
