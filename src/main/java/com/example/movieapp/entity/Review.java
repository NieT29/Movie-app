package com.example.movieapp.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(nullable = false)
    String content;

    @Column(nullable = false)
    Integer rating;

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
